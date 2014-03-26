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
package com.codenvy.ide.ext.datasource.client.common;

import com.google.gwt.user.cellview.client.CellTable;

public interface CellTableResourcesInvisible extends CellTable.Resources {

    interface CellTableStyle extends CellTable.Style {
    }

    @Source({"CellTable-invisible.css", "com/codenvy/ide/api/ui/style.css"})
    CellTableStyle cellTableStyle();

}
