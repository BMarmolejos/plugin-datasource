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

import com.google.gwt.i18n.client.Messages;

public interface EditDatasourceMessages extends Messages {

    @DefaultMessage("Edit Datasource")
    String editButtonText();

    @DefaultMessage("Delete Datasources")
    String deleteButtonText();

    @DefaultMessage("Close")
    String closeButtonText();

    @DefaultMessage("Manage Datasources")
    String editDatasourcesMenuText();

    @DefaultMessage("Create, edit and delete datasources")
    String editDatasourcesMenuDescription();

    @DefaultMessage("Manage datasources")
    String editDatasourcesDialogText();

    @DefaultMessage("Datasources")
    String datasourcesListLabel();

    @DefaultMessage("Cannot edit more than one datasource")
    String editMultipleSelectionMessage();

    @DefaultMessage("No datasource selected")
    String editOrDeleteNoSelectionMessage();

    @DefaultMessage("Delete {0} datasources")
    String confirmDeleteDatasources(int size);

    @DefaultMessage("No datasources configured")
    String emptyDatasourceList();
}
