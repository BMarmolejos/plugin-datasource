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
package com.codenvy.ide.ext.datasource.client.explorer;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;

/**
 * Constant interface for the datasource explorer.
 * 
 * @author "Mickaël Leduque"
 */
@DefaultLocale("en")
public interface DatasourceExplorerConstants extends Messages {

    /** The tooltip for the refresh button. */
    @DefaultMessage("Refresh and explore")
    String exploreButtonTooltip();

    /** The string used in the part (top). */
    @DefaultMessage("Datasource Explorer")
    String datasourceExplorerPartTitle();

    /** The string used in the side tab. */
    @DefaultMessage("Datasource")
    String datasourceExplorerTabTitle();
}
