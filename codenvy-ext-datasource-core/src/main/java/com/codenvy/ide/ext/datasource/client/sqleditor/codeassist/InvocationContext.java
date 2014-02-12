/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 * [2014] Codenvy, S.A.
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

package com.codenvy.ide.ext.datasource.client.sqleditor.codeassist;

import com.codenvy.ide.ext.datasource.client.sqleditor.SqlEditorResources;
import com.codenvy.ide.texteditor.api.TextEditorPartView;

public class InvocationContext {
    private final SqlCodeQuery       query;

    private final int                offset;

    private final SqlEditorResources resources;

    private final TextEditorPartView editor;

    public InvocationContext(SqlCodeQuery query, int offset, SqlEditorResources resources, TextEditorPartView editor) {
        super();
        this.query = query;
        this.offset = offset;
        this.resources = resources;
        this.editor = editor;
    }

    public SqlCodeQuery getQuery() {
        return query;
    }

    public int getOffset() {
        return offset;
    }

    public SqlEditorResources getResources() {
        return resources;
    }

    public TextEditorPartView getEditor() {
        return editor;
    }
}
