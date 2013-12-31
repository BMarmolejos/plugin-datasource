/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 * [2013] - [2014] Codenvy, S.A.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.ide.ext.datasource.inject;

import com.codenvy.ide.api.extension.ExtensionGinModule;
import com.codenvy.ide.api.ui.wizard.DefaultWizard;
import com.codenvy.ide.ext.datasource.action.NewDSConnectionWizard;
import com.codenvy.ide.ext.datasource.action.NewDatasourceWizard;
import com.codenvy.ide.ext.datasource.action.NewDatasourceWizardProvider;
import com.codenvy.ide.ext.datasource.action.wizard.NewDatasourceView;
import com.codenvy.ide.ext.datasource.action.wizard.NewDatasourceViewImpl;
import com.codenvy.ide.ext.datasource.client.DatasourceClientService;
import com.codenvy.ide.ext.datasource.client.DatasourceClientServiceImpl;
import com.codenvy.ide.ext.datasource.connector.ConnectorAgent;
import com.codenvy.ide.ext.datasource.connector.ConnectorAgentImpl;
import com.codenvy.ide.ext.datasource.connector.pg.PgConnectorView;
import com.codenvy.ide.ext.datasource.connector.pg.PgConnectorViewImpl;
import com.codenvy.ide.ext.datasource.explorer.part.DatasourceExplorerView;
import com.codenvy.ide.ext.datasource.explorer.part.DatasourceExplorerViewImpl;
import com.codenvy.ide.ext.datasource.part.DatasourceView;
import com.codenvy.ide.ext.datasource.part.DatasourceViewImpl;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

@ExtensionGinModule
public class DatasourceGinModule extends AbstractGinModule {
    /** {@inheritDoc} */
    @Override
    protected void configure() {
        bind(DatasourceView.class).to(DatasourceViewImpl.class)
                                  .in(Singleton.class);
        bind(DatasourceExplorerView.class).to(DatasourceExplorerViewImpl.class)
                                          .in(Singleton.class);
        bind(DatasourceClientService.class).to(DatasourceClientServiceImpl.class)
                                           .in(Singleton.class);
        bind(DefaultWizard.class).annotatedWith(NewDatasourceWizard.class)
                                 .toProvider(NewDatasourceWizardProvider.class)
                                 .in(Singleton.class);
        bind(ConnectorAgent.class).to(ConnectorAgentImpl.class).in(Singleton.class);
        bind(NewDatasourceView.class).to(NewDatasourceViewImpl.class);
        bind(PgConnectorView.class).to(PgConnectorViewImpl.class);

    }
}
