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
package com.codenvy.ide.ext.datasource.shared;

import java.util.List;
import java.util.Map;

import com.codenvy.dto.shared.DTO;

@DTO
public interface TableDTO extends DatabaseMetadataEntityDTO {

    TableDTO withName(String name);

    TableDTO withLookupKey(String lookupKey);

    TableDTO withIsView(boolean b);

    TableDTO withColumns(Map<String, ColumnDTO> columns);

    TableDTO withType(String tableType);

    TableDTO withPrimaryKey(List<String> primaryKey);

    boolean getIsView();

    void setIsView(boolean b);

    Map<String, ColumnDTO> getColumns();

    void setColumns(Map<String, ColumnDTO> columns);

    void setType(String tabletype);

    String getType();

    void setPrimaryKey(List<String> primaryKey);

    List<String> getPrimaryKey();
}
