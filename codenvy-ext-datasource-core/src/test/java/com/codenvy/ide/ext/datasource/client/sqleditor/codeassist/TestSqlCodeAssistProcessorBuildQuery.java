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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.codenvy.ide.collections.Array;
import com.codenvy.ide.ext.datasource.client.DatabaseInfoOracle;
import com.codenvy.ide.ext.datasource.client.common.ReadableContentTextEditor;
import com.codenvy.ide.ext.datasource.client.sqleditor.EditorDatasourceOracle;
import com.codenvy.ide.ext.datasource.client.sqleditor.SqlEditorResources;
import com.google.gwt.dev.util.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class TestSqlCodeAssistProcessorBuildQuery {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    protected ReadableContentTextEditor textEditor;

    @Mock
    protected SqlEditorResources        resources;

    @Mock
    protected DatabaseInfoOracle        databaseInfoOracle;

    @Mock
    protected EditorDatasourceOracle    editorDatasourceOracle;

    protected SqlCodeAssistProcessor    codeAssistProcessor;

    @Before
    public void init() {
        String editorId = "testEditorInputId";
        when(textEditor.getEditorInput().getFile().getId()).thenReturn(editorId);
        when(editorDatasourceOracle.getSelectedDatasourceId(editorId)).thenReturn("datasourceId");
        when(databaseInfoOracle.getSchemasFor("datasourceId")).thenReturn(Lists.create("public", "meta"));
        when(databaseInfoOracle.getTablesFor("datasourceId", "public")).thenReturn(Lists.create("table", "atable"));
        when(databaseInfoOracle.getTablesFor("datasourceId", "meta")).thenReturn(Lists.create("metaTable", "aMetaTable"));

        codeAssistProcessor = new SqlCodeAssistProcessor(textEditor, resources, databaseInfoOracle, editorDatasourceOracle);
    }


    @Test
    public void completeSelectTemplate() {
        Array<SqlCodeCompletionProposal> results = codeAssistProcessor.findAutoCompletions(new SqlCodeQuery("SELEC"));
        Assert.assertEquals("For number of results for SELEC autocompletion, we expect ", 3, results.size());
    }

    @Test
    public void completeInsertTemplate() {
        Array<SqlCodeCompletionProposal> results = codeAssistProcessor.findAutoCompletions(new SqlCodeQuery("inser"));
        assertEquals("For number of results for inser autocompletion, we expect ", 1, results.size());
        assertEquals("Replacement String should be", "INSERT INTO table (column1, column2) VALUES ('value1', 0);",
                     results.get(0).replacementString);
    }

    @Test
    public void completeTableForSelectFrom() {
        Array<SqlCodeCompletionProposal> results = codeAssistProcessor.findTableAutocompletions(new SqlCodeQuery("Select * from "));
        assertEquals("for number of results for table autocompletion, we expect ", 4, results.size());
    }

    @Test
    public void completeTableForFirstLetterSelectFrom() {
        Array<SqlCodeCompletionProposal> results = codeAssistProcessor.findTableAutocompletions(new SqlCodeQuery("Select * from t"));
        assertEquals("for number of results for table autocompletion starting with t, we expect ", 1, results.size());
    }

    @Test
    public void completeTableForSelectFromMultipleTable() {
        Array<SqlCodeCompletionProposal> results = codeAssistProcessor.findTableAutocompletions(new SqlCodeQuery("Select * from atable, "));
        assertEquals("for number of results for table autocompletion (with multiple tables selected), we expect ", 4, results.size());
    }

    @Test
    public void completeTableForSelectFromFourthTable() {
        Array<SqlCodeCompletionProposal> results = codeAssistProcessor.findTableAutocompletions(
                                                                      new SqlCodeQuery("Select * from atable, another, table, "));
        assertEquals("for number of results for table autocompletion (with 4+ tables selected), we expect ", 4, results.size());
    }

    @Test
    public void completeTableForSelectFromTableWithSchema() {
        Array<SqlCodeCompletionProposal> results =
                                                   codeAssistProcessor.findTableAutocompletions(new SqlCodeQuery(
                                                                                                                 "Select * from schema.atable, "));
        assertEquals("for number of results for table autocompletion with a table with schema selected), we expect ", 4, results.size());
    }

    @Test
    public void completeTableForSelectFromWithCarriageRuturns() {
        Array<SqlCodeCompletionProposal> results =
                                                   codeAssistProcessor.findTableAutocompletions(new SqlCodeQuery(
                                                                                                                 "select * \nfrom "));
        assertEquals("for number of results for table autocompletion with a statement with CR, we expect ", 4, results.size());
    }

    @Test
    public void completeTableWithFullName() {
        Array<SqlCodeCompletionProposal> results =
                                                   codeAssistProcessor.findTableAutocompletions(new SqlCodeQuery(
                                                                                                                 "select * \nfrom pu"));
        assertEquals("for number of results for table autocompletion when starting writing public, we expect ", 2, results.size());
    }

}