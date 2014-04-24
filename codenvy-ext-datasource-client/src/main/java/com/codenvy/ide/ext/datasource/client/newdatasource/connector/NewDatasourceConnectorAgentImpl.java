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

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import com.codenvy.ide.api.ui.wizard.DefaultWizard;
import com.codenvy.ide.ext.datasource.client.editdatasource.wizard.EditDatasourceWizard;
import com.codenvy.ide.ext.datasource.client.editdatasource.wizard.EditDatasourceWizardQualifier;
import com.codenvy.ide.ext.datasource.client.newdatasource.NewDatasourceWizardQualifier;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewDatasourceConnectorAgentImpl implements NewDatasourceConnectorAgent {

    private final DefaultWizard                     newDatasourceWizard;
    private final DefaultWizard                     editDatasourceWizard;

    private final SortedSet<NewDatasourceConnector> registeredConnectors;

    @Inject
    public NewDatasourceConnectorAgentImpl(final @NewDatasourceWizardQualifier DefaultWizard newDatasourceWizard,
                                           final @EditDatasourceWizardQualifier EditDatasourceWizard editDatasourceWizard) {
        this.newDatasourceWizard = newDatasourceWizard;
        this.editDatasourceWizard = editDatasourceWizard;
        registeredConnectors = new TreeSet<NewDatasourceConnector>();
    }

    @Override
    public void register(final NewDatasourceConnector connector) {
        if (registeredConnectors.contains(connector)) {
            // TODO this shouldn't happen: notification error instead of the alert ?
            Window.alert("Datasource connector with " + connector.getId() + " id already exists");
            return;
        }

        registeredConnectors.add(connector);
        for (Provider< ? extends AbstractNewDatasourceConnectorPage> provider : connector.getWizardPages().asIterable()) {
            newDatasourceWizard.addPage(provider);
            editDatasourceWizard.addPage(provider);
        }
    }

    @Override
    public Collection<NewDatasourceConnector> getConnectors() {
        return registeredConnectors;
    }

    @Override
    public NewDatasourceConnector getConnector(final String id) {
        if (id == null) {
            return null;
        }
        for (final NewDatasourceConnector connector : registeredConnectors) {
            if (id.equals(connector.getId())) {
                return connector;
            }
        }
        return null;
    }
}
