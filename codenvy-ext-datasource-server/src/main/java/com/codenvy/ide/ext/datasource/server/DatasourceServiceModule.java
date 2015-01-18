/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.datasource.server;

import com.codenvy.ide.ext.datasource.server.ssl.KeyStoreObject;
import com.codenvy.ide.ext.datasource.server.ssl.SslKeyStoreService;
import com.codenvy.ide.ext.datasource.server.ssl.TrustStoreObject;
import com.codenvy.inject.DynaModule;
import com.google.inject.AbstractModule;

/**
 * Bindings for the datasource-related services.
 *
 * @author "Mickaël Leduque"
 */
@DynaModule
public class DatasourceServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JdbcConnectionFactory.class);
        bind(SqlRequestService.class);
        bind(KeyStoreObject.class);
        bind(TrustStoreObject.class);
        bind(SslKeyStoreService.class);
        bind(AvailableDriversService.class);
        bind(DatabaseExploreService.class);
        bind(CsvExportService.class);
        bind(TestConnectionService.class);
        bind(EncryptTextService.class);
    }
}
