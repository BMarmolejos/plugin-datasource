package com.codenvy.ide.ext.datasource.client.sqleditor;

import com.codenvy.ide.api.ui.action.Action;
import com.codenvy.ide.api.ui.action.ActionEvent;
import com.google.inject.Inject;

/**
 * Action to launch and show a SQL editor.
 * 
 * @author "Mickaël Leduque"
 */
public class SqlRequestLauncherAction extends Action {
    @Inject
    public SqlRequestLauncherAction(final SqlRequestLauncherConstants constants) {
        super(constants.menuEntryOpenSqlEditor());
    }

    @Override
    public void actionPerformed(final ActionEvent e) {

    }

}
