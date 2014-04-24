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
package com.codenvy.ide.ext.datasource.client.newdatasource.connector;

import javax.annotation.Nullable;

import com.codenvy.ide.ext.datasource.client.DatasourceUiResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;


public class DefaultNewDatasourceConnectorViewImpl extends Composite
                                                                    implements DefaultNewDatasourceConnectorView {
    interface NewDatasourceViewImplUiBinder extends UiBinder<Widget, DefaultNewDatasourceConnectorViewImpl> {
    }

    @UiField
    SimplePanel            imagePanel;

    @UiField
    Label                  configureTitleCaption;

    @UiField
    TextBox                hostField;

    @UiField
    TextBox                portField;

    @UiField
    TextBox                dbName;

    @UiField
    TextBox                usernameField;

    @UiField
    PasswordTextBox        passwordField;

    @UiField
    Button                 testConnectionButton;

    @UiField
    Label                  testConnectionErrorMessage;

    @UiField
    CheckBox               useSSL;

    @UiField
    CheckBox               verifyServerCertificate;

    @UiField
    DatasourceUiResources  datasourceUiResources;

    private ActionDelegate delegate;


    @Inject
    public DefaultNewDatasourceConnectorViewImpl(NewDatasourceViewImplUiBinder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        hostField.setText("localhost");
    }

    public void setDelegate(DefaultNewDatasourceConnectorView.ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void setImage(@Nullable ImageResource image) {
        imagePanel.setWidget(image == null ? null : new Image(image));
    }

    @Override
    public void setDatasourceName(@Nullable String dsName) {
        configureTitleCaption.setText("Configure a " + dsName + " datasource:");
    }

    @Override
    public String getDatabaseName() {
        return dbName.getText();
    }

    @Override
    public String getHostname() {
        return hostField.getText();
    }

    @Override
    public int getPort() {
        return Integer.parseInt(portField.getText());
    }

    @Override
    public String getUsername() {
        return usernameField.getText();
    }

    @Override
    public String getPassword() {
        return passwordField.getText();
    }

    @Override
    public void setPort(int port) {
        portField.setText(Integer.toString(port));
    }

    @Override
    public boolean getUseSSL() {
        if (useSSL.getValue() != null) {
            return useSSL.getValue();
        } else {
            return false;
        }
    }

    @Override
    public boolean getVerifyServerCertificate() {
        if (verifyServerCertificate.getValue() != null) {
            return verifyServerCertificate.getValue();
        } else {
            return false;
        }
    }

    @Override
    public void setDatabaseName(final String databaseName) {
        this.dbName.setValue(databaseName);
    }

    @Override
    public void setHostName(final String hostName) {
        this.hostField.setValue(hostName);
    }

    @Override
    public void setUseSSL(final boolean useSSL) {
        this.useSSL.setValue(useSSL);
    }

    @Override
    public void setVerifyServerCertificate(final boolean verifyServerCertificate) {
        this.verifyServerCertificate.setValue(verifyServerCertificate);
    }

    @Override
    public void setUsername(final String username) {
        this.usernameField.setValue(username);
    }

    @Override
    public void setPassword(final String password) {
        this.passwordField.setValue(password);
    }

    @UiHandler("testConnectionButton")
    void handleClick(ClickEvent e) {
        delegate.onClickTestConnectionButton();
    }

    @Override
    public void onTestConnectionSuccess() {
        // turn button green
        testConnectionButton.setStyleName(datasourceUiResources.datasourceUiCSS().datasourceWizardTestConnectionOK());
        // clear error messages
        testConnectionErrorMessage.setText("");
    }

    @Override
    public void onTestConnectionFailure(String errorMessage) {
        // turn test button red
        testConnectionButton.setStyleName(datasourceUiResources.datasourceUiCSS().datasourceWizardTestConnectionKO());
        // set message
        testConnectionErrorMessage.setText(errorMessage);

    }
}
