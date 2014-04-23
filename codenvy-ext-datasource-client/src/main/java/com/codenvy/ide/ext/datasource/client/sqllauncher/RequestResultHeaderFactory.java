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

package com.codenvy.ide.ext.datasource.client.sqllauncher;

import com.codenvy.ide.ext.datasource.client.sqllauncher.RequestResultHeader.RequestResultDelegate;
import com.codenvy.ide.ext.datasource.shared.request.RequestResultDTO;

/**
 * Factory for {@link RequestResultHeader}.
 * 
 * @author "Mickaël Leduque"
 */
public interface RequestResultHeaderFactory {

    /**
     * Creates an instance of {@link RequestResultHeader}.
     * 
     * @param delegate the action delegate
     * @return a {@link RequestResultDelegate}
     */
    RequestResultHeader createRequestResultHeader(RequestResultDelegate delegate, String query);

    /**
     * Creates an instance of {@link RequestResultHeader} with a CSV export button.
     * 
     * @param delegate the action delegate
     * @return a {@link RequestResultDelegate}
     */
    RequestResultHeader createRequestResultHeaderWithExport(RequestResultDelegate delegate, RequestResultDTO result);
}
