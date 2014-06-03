/*******************************************************************************
 * Copyright (c) 2012-2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.datasource.client.newdatasource.connector.postgres;

import com.codenvy.ide.api.notification.NotificationManager;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.datasource.client.DatasourceClientService;
import com.codenvy.ide.ext.datasource.client.DatasourceUiResources;
import com.codenvy.ide.ext.datasource.client.newdatasource.NewDatasourceWizardMessages;
import com.codenvy.ide.ext.datasource.client.newdatasource.connector.DefaultNewDatasourceConnectorPage;
import com.codenvy.ide.ext.datasource.client.newdatasource.connector.DefaultNewDatasourceConnectorView;
import com.codenvy.ide.ext.datasource.client.store.DatasourceManager;
import com.codenvy.ide.ext.datasource.shared.DatabaseType;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class PostgresDatasourceConnectorPage extends DefaultNewDatasourceConnectorPage {

    public static final String PG_DB_ID           = "postgres";
    private static final int   DEFAULT_PORT_PGSQL = 5432;


    @Inject
    public PostgresDatasourceConnectorPage(final DefaultNewDatasourceConnectorView view,
                                           final NotificationManager notificationManager,
                                           final DtoFactory dtoFactory,
                                           final DatasourceManager datasourceManager,
                                           final EventBus eventBus,
                                           final DatasourceClientService service,
                                           final DatasourceUiResources resources,
                                           final NewDatasourceWizardMessages messages) {
        super(view, messages.postgresql(), resources.getPostgreSqlLogo(), PG_DB_ID, datasourceManager, eventBus, service, notificationManager,
              dtoFactory, messages, DEFAULT_PORT_PGSQL, DatabaseType.POSTGRES);
    }

}
