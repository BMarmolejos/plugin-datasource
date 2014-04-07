/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codenvy.ide.ext.datasource.client.editdatasource;

import java.util.Set;

import com.codenvy.api.user.shared.dto.Profile;
import com.codenvy.ide.api.notification.Notification;
import com.codenvy.ide.api.notification.Notification.Status;
import com.codenvy.ide.api.notification.Notification.Type;
import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.ext.datasource.client.DatasourceManager;
import com.codenvy.ide.ext.datasource.client.editdatasource.wizard.EditDatasourceLauncher;
import com.codenvy.ide.ext.datasource.client.events.DatasourceListChangeEvent;
import com.codenvy.ide.ext.datasource.shared.DatabaseConfigurationDTO;
import com.codenvy.ide.util.loging.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The presenter for the datasource edit/delete datasources dialog.
 * 
 * @author "Mickaël Leduque"
 */
public class EditDatasourcesPresenter implements EditDatasourcesView.ActionDelegate {

    /** The view component. */
    private final EditDatasourcesView                           view;

    /** The component that stores datasources. */
    private final DatasourceManager                             datasourceManager;

    /** The datasource list model component. */
    private final ListDataProvider<DatabaseConfigurationDTO>    dataProvider = new ListDataProvider<>();
    /** The selection model for the datasource list widget. */
    private final MultiSelectionModel<DatabaseConfigurationDTO> selectionModel;

    /** The i18n messages instance. */
    private final EditDatasourceMessages                        messages;

    private final NotificationManager                           notificationManager;

    /** the event bus, used to send event "datasources list modified". */
    private final EventBus                                      eventBus;

    /** A factory that will provide modification wizards. */
    private EditDatasourceLauncher                              editDatasourceLauncher;

    @Inject
    public EditDatasourcesPresenter(final EditDatasourcesView view,
                                    final DatasourceManager datasourceManager,
                                    final @Named(DatasourceKeyProvider.NAME) DatasourceKeyProvider keyProvider,
                                    final EditDatasourceMessages messages,
                                    final NotificationManager notificationManager,
                                    final EventBus eventBus,
                                    final EditDatasourceLauncher editDatasourceLauncher) {
        this.view = view;
        this.datasourceManager = datasourceManager;
        this.messages = messages;
        this.notificationManager = notificationManager;
        this.eventBus = eventBus;
        this.editDatasourceLauncher = editDatasourceLauncher;
        this.view.bindDatasourceModel(dataProvider);
        this.view.setDelegate(this);
        this.selectionModel = new MultiSelectionModel<>(keyProvider);
        this.view.bindSelectionModel(this.selectionModel);
    }

    /** Show dialog. */
    public void showDialog() {
        setupDatasourceList();
        this.view.showDialog();
    }

    @Override
    public void closeDialog() {
        this.view.closeDialog();

    }

    /** Sets the content of the datasource widget. */
    private void setupDatasourceList() {
        this.dataProvider.getList().clear();
        for (DatabaseConfigurationDTO datasource : this.datasourceManager) {
            this.dataProvider.getList().add(datasource);
        }
        this.dataProvider.flush();
    }

    @Override
    public void deleteSelectedDatasources() {
        final Set<DatabaseConfigurationDTO> selection = this.selectionModel.getSelectedSet();
        if (selection.isEmpty()) {
            Window.alert(this.messages.editOrDeleteNoSelectionMessage());
            return;
        }
        if (!Window.confirm(messages.confirmDeleteDatasources(selection.size()))) {
            return;
        }
        for (final DatabaseConfigurationDTO datasource : selection) {
            this.dataProvider.getList().remove(datasource);
            this.datasourceManager.remove(datasource);
        }
        final Notification persistNotification = new Notification("Saving datasources definitions", Status.PROGRESS);
        this.notificationManager.showNotification(persistNotification);
        try {
            this.datasourceManager.persist(new AsyncCallback<Profile>() {

                @Override
                public void onSuccess(final Profile result) {
                    Log.info(EditDatasourcesPresenter.class, "Datasource definitions saved.");
                    persistNotification.setMessage("Datasource definitions saved");
                    persistNotification.setStatus(Notification.Status.FINISHED);

                }

                @Override
                public void onFailure(final Throwable e) {
                    Log.error(EditDatasourcesPresenter.class, "Exception when persisting datasources: " + e.getMessage());
                    GWT.log("Full exception :", e);
                    notificationManager.showNotification(new Notification("Failed to persist datasources", Type.ERROR));

                }
            });
        } catch (final Exception e) {
            Log.error(EditDatasourcesPresenter.class, "Exception when persisting datasources: " + e.getMessage());
            notificationManager.showNotification(new Notification("Failed to persist datasources", Type.ERROR));
        }

        // reset datasource model
        setupDatasourceList();
        // inform the world about the datasource list modification
        this.eventBus.fireEvent(new DatasourceListChangeEvent());
    }

    @Override
    public void editSelectedDatasource() {
        final Set<DatabaseConfigurationDTO> selection = this.selectionModel.getSelectedSet();
        if (selection.isEmpty()) {
            Window.alert(this.messages.editOrDeleteNoSelectionMessage());
            return;
        }
        if (selection.size() > 1) {
            Window.alert(this.messages.editMultipleSelectionMessage());
            return;
        }

        for (DatabaseConfigurationDTO datasource : selection) { // there is only one !
            this.editDatasourceLauncher.launch(datasource);
        }

        // reset datasource model
        setupDatasourceList();
        // inform the world about the datasource list modification
        this.eventBus.fireEvent(new DatasourceListChangeEvent());
    }
}
