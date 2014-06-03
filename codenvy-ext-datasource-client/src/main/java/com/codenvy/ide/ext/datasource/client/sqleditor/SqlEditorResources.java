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
package com.codenvy.ide.ext.datasource.client.sqleditor;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

public interface SqlEditorResources extends ClientBundle {

    @Source("codemirror-parser-sql.js")
    TextResource sqlParserJs();

    @Source("sql-icon.png")
    ImageResource sqlFile();

    @Source("sql-completion.png")
    ImageResource sqlCompletionIcon();

    @Source({"sql.css", "com/codenvy/ide/api/ui/style.css"})
    CssResource sqlCSS();
}
