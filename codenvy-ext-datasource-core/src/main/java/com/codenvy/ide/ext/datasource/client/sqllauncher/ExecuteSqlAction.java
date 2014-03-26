/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 * [2013] - [2014] Codenvy, S.A.
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
package com.codenvy.ide.ext.datasource.client.sqllauncher;

import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.codenvy.ide.util.loging.Log;
import com.google.inject.Inject;

public class ExecuteSqlAction extends Action {

    private EditorAgent editorAgent;

    @Inject
    public ExecuteSqlAction(final EditorAgent editorAgent) {
        this.editorAgent = editorAgent;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final EditorPartPresenter editor = editorAgent.getActiveEditor();
        if (editor instanceof SqlRequestLauncherPresenter) {
            Log.info(ExecuteSqlAction.class, "Execute SQL action triggered.");
            SqlRequestLauncherPresenter sqlEditor = (SqlRequestLauncherPresenter)editor;
            sqlEditor.executeRequested();
        } else {
            Log.warn(ExecuteSqlAction.class, "Execute SQL action triggered but active editor is not compatible.");
        }

    }

}
