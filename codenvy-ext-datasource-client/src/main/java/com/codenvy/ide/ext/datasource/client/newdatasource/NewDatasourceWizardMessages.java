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
package com.codenvy.ide.ext.datasource.client.newdatasource;

import com.google.gwt.i18n.client.Messages;

public interface NewDatasourceWizardMessages extends Messages {

    @DefaultMessage("New Datasource Connection")
    String newDatasourceMenuText();

    @DefaultMessage("Create a new datasource connection")
    String newDatasourceMenuDescription();

    @DefaultMessage("New Datasource")
    String wizardTitle();

    @DefaultMessage("An error occured while creating the datasource connection")
    String defaultNewDatasourceWizardErrorMessage();

    @DefaultMessage("Establishing Database Connection...")
    String startConnectionTest();

    @DefaultMessage("Succesfully connected to database")
    String connectionTestSuccessNotification();

    @DefaultMessage("Failed to connect to database")
    String connectionTestFailureSuccessNotification();

    @DefaultMessage("Connection succeeded")
    String connectionTestSuccessMessage();

    @DefaultMessage("Connection failed")
    String connectionTestFailureSuccessMessage();
}
