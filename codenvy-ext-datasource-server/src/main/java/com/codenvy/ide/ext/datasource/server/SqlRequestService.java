/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codenvy.ide.ext.datasource.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codenvy.dto.server.DtoFactory;
import com.codenvy.ide.ext.datasource.shared.MultipleRequestExecutionMode;
import com.codenvy.ide.ext.datasource.shared.RequestParameterDTO;
import com.codenvy.ide.ext.datasource.shared.ServicePaths;
import com.codenvy.ide.ext.datasource.shared.exception.BadSQLRequestParameterException;
import com.codenvy.ide.ext.datasource.shared.exception.DatabaseDefinitionException;
import com.codenvy.ide.ext.datasource.shared.request.ExecutionErrorResultDTO;
import com.codenvy.ide.ext.datasource.shared.request.RequestResultDTO;
import com.codenvy.ide.ext.datasource.shared.request.RequestResultGroupDTO;
import com.codenvy.ide.ext.datasource.shared.request.SelectResultDTO;
import com.codenvy.ide.ext.datasource.shared.request.SqlExecutionError;
import com.codenvy.ide.ext.datasource.shared.request.UpdateResultDTO;
import com.google.common.base.Splitter;
import com.google.common.math.LongMath;
import com.google.inject.Inject;

/**
 * Service that handles SQL execution requests.
 * 
 * @author "Mickaël Leduque"
 */
@Path(ServicePaths.EXECUTE_SQL_REQUEST_PATH)
public class SqlRequestService {

    /** The logger. */
    private static final Logger                      LOG                   = LoggerFactory.getLogger(SqlRequestService.class);

    /** The delimiter used to split SQL requests. */
    private static final String                      SQL_REQUEST_DELIMITER = ";";

    /** The splitter instance used to split SQL request in a query string. */
    private static final Splitter                    SQL_REQUEST_SPLITTER  = Splitter.on(SQL_REQUEST_DELIMITER)
                                                                                     .omitEmptyStrings()
                                                                                     .trimResults();

    public static final MultipleRequestExecutionMode DEFAULT_MODE          = MultipleRequestExecutionMode.ONE_BY_ONE;

    /** The factory to create JDBC connections from datasource definitions. */
    private JdbcConnectionFactory                    jdbcConnectionFactory;

    @Inject
    public SqlRequestService(final JdbcConnectionFactory jdbcConnectionFactory) {
        this.jdbcConnectionFactory = jdbcConnectionFactory;
    }

    /**
     * Executes the SQL requests given as parameter.
     * 
     * @param request the requests parameters
     * @return a result object, either success (with data) or failure (with message)
     * @throws SQLException if the execution caused an error
     * @throws DatabaseDefinitionException if the datasource is not correctly defined
     */
    // same path as 'class'
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String executeSqlRequest(final RequestParameterDTO request) throws SQLException,
                                                                      DatabaseDefinitionException,
                                                                      BadSQLRequestParameterException {
        checkParameters(request);
        try (final Connection connection = this.jdbcConnectionFactory.getDatabaseConnection(request.getDatabase())) {

            MultipleRequestExecutionMode mode = SqlRequestService.DEFAULT_MODE;
            if (request.getMultipleRequestExecutionMode() != null) {
                mode = request.getMultipleRequestExecutionMode();
            }

            long startTime = System.currentTimeMillis();
            final RequestResultGroupDTO resultGroup = executeSqlRequest(request, connection, mode);
            long endExecTime = System.currentTimeMillis();

            String json = DtoFactory.getInstance().toJson(resultGroup);
            long endJsonTime = System.currentTimeMillis();
            try {
                LOG.debug("Execution of SQL request '{}' with result limit {} - sql duration={}, json conversion duration={}",
                          request.getSqlRequest(), request.getResultLimit(),
                          LongMath.checkedSubtract(endExecTime, startTime),
                          LongMath.checkedSubtract(endJsonTime, endExecTime));
            } catch (final ArithmeticException e) {
                LOG.debug("Execution of SQL request '{}' with result limit {} - unknwown durations");
            }
            LOG.trace("Return {}", json);
            return json;
        }
    }

    /**
     * Execute the request string using the provided connection.
     * 
     * @param requestParameter a string containing one or more SQL requests
     * @param connection the conenction the requests will be perfomred on
     * @param mode the way errors are handled (stop, continue)
     * @return a result object
     * @throws SQLException when the requests could not be executed (NOT a SQL error caused by one requests, those are handled)
     */
    public RequestResultGroupDTO executeSqlRequest(final RequestParameterDTO requestParameter,
                                                   final Connection connection,
                                                   final MultipleRequestExecutionMode mode) throws SQLException {

        LOG.debug("Execution request ; parameter : {}", requestParameter);
        final String agglutinatedRequests = requestParameter.getSqlRequest();
        Iterable<String> requests = SQL_REQUEST_SPLITTER.split(agglutinatedRequests); // TODO allow ; inside /* ... */ or '...'

        // prepare result dto
        final RequestResultGroupDTO resultGroup = DtoFactory.getInstance().createDto(RequestResultGroupDTO.class);
        final List<RequestResultDTO> resultList = new ArrayList<>();
        resultGroup.setResults(resultList);

        boolean terminate = false;
        for (final String request : requests) {
            try (final Statement statement = connection.createStatement();) {
                statement.setMaxRows(requestParameter.getResultLimit());
                resultList.addAll(processSingleRequest(request, statement));
            } catch (final SQLException e) {
                final ExecutionErrorResultDTO errorDto = DtoFactory.getInstance().createDto(ExecutionErrorResultDTO.class);
                final SqlExecutionError sqlError = DtoFactory.getInstance().createDto(SqlExecutionError.class);
                errorDto.setSqlExecutionError(sqlError);
                errorDto.setOriginRequest(request);
                sqlError.withErrorCode(e.getErrorCode())
                        .withErrorMessage(e.getMessage());
                resultList.add(errorDto);

                switch (mode) {
                    case STOP_AT_FIRST_ERROR:
                        terminate = true;
                        break;
                    case TRANSACTIONAL:
                        throw new RuntimeException("transaction execution mode is not impemented");
                    default:
                        break;
                }
            }
            if (terminate) {
                break;
            }
        }

        return resultGroup;
    }

    /**
     * Execute ONE SQL request.
     * 
     * @param request the request
     * @param statement the statement used to execute the request
     * @return a list of execution result objects
     * @throws SQLException when the request execution failed (NOT a SQL error caused by the request)
     */
    private List<RequestResultDTO> processSingleRequest(final String request, final Statement statement) throws SQLException {
        statement.execute(request);
        LOG.debug("Request executed successfully");

        ResultSet resultSet = statement.getResultSet();
        int count = statement.getUpdateCount();

        List<RequestResultDTO> resultList = new ArrayList<>();
        while (resultSet != null || count != -1) {
            LOG.trace("New result returned by request :");

            if (count != -1) {
                LOG.trace("   is an update count");
                final UpdateResultDTO result = DtoFactory.getInstance().createDto(UpdateResultDTO.class);
                resultList.add(result);
                result.withResultType(UpdateResultDTO.TYPE)
                      .withUpdateCount(count)
                      .withOriginRequest(request);
            } else {
                LOG.trace("   is a result set");
                final SelectResultDTO result = DtoFactory.getInstance().createDto(SelectResultDTO.class);
                result.withResultType(SelectResultDTO.TYPE)
                      .withOriginRequest(request);
                resultList.add(result);

                final ResultSetMetaData metadata = resultSet.getMetaData();
                final int columnCount = metadata.getColumnCount();

                // header : column names
                final List<String> columnNames = new ArrayList<>();
                for (int i = 1; i < columnCount + 1; i++) {
                    columnNames.add(metadata.getColumnLabel(i));
                }
                result.setHeaderLine(columnNames);

                final List<List<String>> lines = new ArrayList<>();

                // result : actual data
                while (resultSet.next()) {
                    final List<String> line = new ArrayList<>();
                    for (int i = 1; i < columnCount + 1; i++) {
                        line.add(resultSet.getString(i));
                    }
                    lines.add(line);
                }
                result.setResultLines(lines);
            }

            // continue the loop - next result

            // getMoreResult should close it, but just to remove the warning
            if (resultSet != null) {
                resultSet.close();
            }
            statement.getMoreResults();
            resultSet = statement.getResultSet();
            count = statement.getUpdateCount();
        }
        return resultList;
    }

    /**
     * Checks the parameters. Thoses are mostly technical errors that the UI should guard against, but double checking protects against UI
     * changes, API changes and direct REST requests.
     * 
     * @param requestParameter the parameters
     * @throws BadSQLRequestParameterException error in the parameters
     */
    private void checkParameters(final RequestParameterDTO requestParameter) throws BadSQLRequestParameterException {
        // check result limit > 0
        final int resultLimit = requestParameter.getResultLimit();
        if (resultLimit < 1) {
            throw new BadSQLRequestParameterException("Result limit must be greater than or equal to 1");
        }
        // check the request string is not null
        if (requestParameter.getSqlRequest() == null) {
            throw new BadSQLRequestParameterException("The request string must not be null");
        }
        // check if the datasource configuration object is null
        // invalid configuration is in another check and has its own dedicated exception
        if (requestParameter.getDatabase() == null) {
            throw new BadSQLRequestParameterException("No datasource configuration provided");
        }
    }
}
