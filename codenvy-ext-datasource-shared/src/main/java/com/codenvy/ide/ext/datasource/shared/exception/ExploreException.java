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

package com.codenvy.ide.ext.datasource.shared.exception;

/**
 * Exception for datasource explore errors.
 * 
 * @author "Mickaël Leduque"
 */
public class ExploreException extends Exception {

    /** UID for serialization */
    private static final long serialVersionUID = 1L;

    public ExploreException() {
    }

    public ExploreException(final String message) {
        super(message);
    }

    public ExploreException(final Throwable cause) {
        super(cause);
    }

    public ExploreException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
