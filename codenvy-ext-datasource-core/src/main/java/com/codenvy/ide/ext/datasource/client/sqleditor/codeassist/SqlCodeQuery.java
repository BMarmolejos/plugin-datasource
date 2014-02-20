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

/**
 * All the elements needed after having parse the context to compute results.
 */
public class SqlCodeQuery {
    private String lastQueryPrefix;

    /**
     * @param lastQueryPrefix : Prefix of the last query in the SQL file or selected element. for instance "SELECT * FRO".
     */
    public SqlCodeQuery(String lastQueryPrefix) {
        this.lastQueryPrefix = lastQueryPrefix;
    }

    public String getLastQueryPrefix() {
        return lastQueryPrefix;
    }

    public void setLastQueryPrefix(String queryPrefix) {
        this.lastQueryPrefix = queryPrefix;
    }
}