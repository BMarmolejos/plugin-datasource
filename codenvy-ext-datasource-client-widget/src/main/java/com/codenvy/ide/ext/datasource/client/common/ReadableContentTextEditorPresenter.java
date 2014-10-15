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
package com.codenvy.ide.ext.datasource.client.common;

import com.codenvy.ide.CoreLocalizationConstant;
import com.codenvy.ide.Resources;
import com.codenvy.ide.api.parts.WorkspaceAgent;
import com.codenvy.ide.debug.BreakpointGutterManager;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.texteditor.TextEditorPresenter;
import com.codenvy.ide.texteditor.selection.SelectionModel;
import com.codenvy.ide.util.executor.UserActivityManager;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class ReadableContentTextEditorPresenter extends TextEditorPresenter implements ReadableContentTextEditor {

    @Inject
    public ReadableContentTextEditorPresenter(final Resources resources,
                                              final UserActivityManager userActivityManager,
                                              final BreakpointGutterManager breakpointGutterManager,
                                              final DtoFactory dtoFactory,
                                              final WorkspaceAgent workspaceAgent,
                                              final EventBus eventBus,
                                              CoreLocalizationConstant constant) {
        super(resources,
              userActivityManager,
              breakpointGutterManager,
              dtoFactory,
              workspaceAgent,
              constant,
              eventBus);
    }

    @Override
    public String getEditorContent() {
        return editor.getTextStore().asText();
    }

    @Override
    public String getSelectedContent() {
        final SelectionModel selectionModel = editor.getSelection();
        return selectionModel.getSelectedText();
    }

}
