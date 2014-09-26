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
package com.codenvy.ide.ext.datasource.client.sqllauncher;

import com.codenvy.api.analytics.logger.AnalyticsEventLogger;
import com.codenvy.ide.api.action.Action;
import com.codenvy.ide.api.action.ActionEvent;
import com.codenvy.ide.api.editor.EditorAgent;
import com.codenvy.ide.api.editor.EditorPartPresenter;
import com.codenvy.ide.util.loging.Log;
import com.google.inject.Inject;

public class ExecuteSqlAction extends Action {

    private EditorAgent editorAgent;

    private final AnalyticsEventLogger eventLogger;

    @Inject
    public ExecuteSqlAction(final EditorAgent editorAgent, AnalyticsEventLogger eventLogger) {
        this.editorAgent = editorAgent;
        this.eventLogger = eventLogger;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        eventLogger.log(this);

        final EditorPartPresenter editor = editorAgent.getActiveEditor();
        if (editor instanceof SqlRequestLauncherPresenter) {
            Log.debug(ExecuteSqlAction.class, "Execute SQL action triggered.");
            SqlRequestLauncherPresenter sqlEditor = (SqlRequestLauncherPresenter)editor;
            sqlEditor.executeRequested();
        } else {
            Log.warn(ExecuteSqlAction.class, "Execute SQL action triggered but active editor is not compatible.");
        }

    }

}
