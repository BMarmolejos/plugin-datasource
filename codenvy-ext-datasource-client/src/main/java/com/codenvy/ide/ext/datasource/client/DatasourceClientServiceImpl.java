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
package com.codenvy.ide.ext.datasource.client;

import javax.validation.constraints.NotNull;

import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.datasource.client.inject.DatasourceGinModule;
import com.codenvy.ide.ext.datasource.shared.DatabaseConfigurationDTO;
import com.codenvy.ide.ext.datasource.shared.ExploreRequestDTO;
import com.codenvy.ide.ext.datasource.shared.ExploreTableType;
import com.codenvy.ide.ext.datasource.shared.MultipleRequestExecutionMode;
import com.codenvy.ide.ext.datasource.shared.RequestParameterDTO;
import com.codenvy.ide.ext.datasource.shared.ServicePaths;
import com.codenvy.ide.ext.datasource.shared.request.RequestResultDTO;
import com.codenvy.ide.rest.AsyncRequest;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.AsyncRequestFactory;
import com.codenvy.ide.util.loging.Log;
import com.google.gwt.http.client.RequestException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Implementation (REST) for the datasource server services client interface.
 */
@Singleton
public class DatasourceClientServiceImpl implements DatasourceClientService {

    private final String              restServiceContext;
    private final DtoFactory          dtoFactory;
    private final AsyncRequestFactory asyncRequestFactory;

    /**
     * @param restContext rest context
     * @param loader loader to show on server request
     */
    @Inject
    protected DatasourceClientServiceImpl(final @Named(DatasourceGinModule.DATASOURCE_CONTEXT_NAME) String restContext,
                                          final DtoFactory dtoFactory,
                                          final AsyncRequestFactory asyncRequestFactory) {
        this.restServiceContext = restContext;
        this.dtoFactory = dtoFactory;
        this.asyncRequestFactory = asyncRequestFactory;
    }

    @Override
    public void fetchDatabaseInfo(final @NotNull DatabaseConfigurationDTO configuration,
                                  final ExploreTableType tableCategory,
                                  final @NotNull AsyncRequestCallback<String> asyncRequestCallback) throws RequestException {
        final String url = formatUrl(this.restServiceContext, ServicePaths.DATABASE_EXPLORE_PATH, "", null);
        ExploreRequestDTO request = this.dtoFactory.createDto(ExploreRequestDTO.class);
        request.setDatasourceConfig(configuration);
        request.setExploreTableType(tableCategory);
        final AsyncRequest postRequest = this.asyncRequestFactory.createPostRequest(url, request, false);
        postRequest.send(asyncRequestCallback);
    }

    @Override
    public void fetchDatabaseInfo(final @NotNull DatabaseConfigurationDTO configuration,
                                  final @NotNull AsyncRequestCallback<String> asyncRequestCallback) throws RequestException {
        fetchDatabaseInfo(configuration, ExploreTableType.STANDARD, asyncRequestCallback);
    }

    @Override
    public void executeSqlRequest(final DatabaseConfigurationDTO configuration,
                                  final int resultLimit,
                                  final String sqlRequest,
                                  final MultipleRequestExecutionMode execMode,
                                  final AsyncRequestCallback<String> asyncRequestCallback)
                                                                                          throws RequestException {
        final String url = formatUrl(this.restServiceContext, ServicePaths.EXECUTE_SQL_REQUEST_PATH, "", null);
        final RequestParameterDTO requestParameterDTO = dtoFactory.createDto(RequestParameterDTO.class)
                                                                  .withDatabase(configuration)
                                                                  .withResultLimit(resultLimit)
                                                                  .withSqlRequest(sqlRequest)
                                                                  .withMultipleRequestExecutionMode(execMode);
        final AsyncRequest postRequest = this.asyncRequestFactory.createPostRequest(url, requestParameterDTO, false);
        postRequest.send(asyncRequestCallback);
    }

    @Override
    public void getAvailableDrivers(AsyncRequestCallback<String> asyncRequestCallback) throws RequestException {
        String url = formatUrl(this.restServiceContext, ServicePaths.DATABASE_TYPES_PATH, "", null);
        final AsyncRequest getRequest = this.asyncRequestFactory.createGetRequest(url, false);
        getRequest.send(asyncRequestCallback);
    }

    @Override
    public String getRestServiceContext() {
        return this.restServiceContext;
    }

    @Override
    public void exportAsCsv(final RequestResultDTO requestResult,
                            final AsyncRequestCallback<String> asyncRequestCallback) throws RequestException {
        String url = formatUrl(this.restServiceContext, ServicePaths.RESULT_CSV_PATH, "", null);
        AsyncRequest postRequest = this.asyncRequestFactory.createPostRequest(url, requestResult, false);
        postRequest.send(asyncRequestCallback);
    }

    @Override
    public void testDatabaseConnectivity(final @NotNull DatabaseConfigurationDTO configuration,
                                         final @NotNull AsyncRequestCallback<String> asyncRequestCallback) throws RequestException {
        String url = formatUrl(this.restServiceContext, ServicePaths.TEST_DATABASE_CONNECTIVITY_PATH, "", null);
        final AsyncRequest postRequest = this.asyncRequestFactory.createPostRequest(url, configuration, false);
        postRequest.send(asyncRequestCallback);
    }

    /**
     * Builds the target REST service url.
     * 
     * @param context the rest context
     * @param root the root of the service
     * @param service the rest service
     * @param param the parameters
     * @return the url
     */
    private String formatUrl(final String context, final String root, final String service, final String param) {
        StringBuilder sb = new StringBuilder(context);
        sb.append("/")
          .append(root);
        if (service != null && !service.isEmpty()) {
            sb.append("/")
              .append(service);
        }

        if (param != null) {
            sb.append('/')
              .append(param);
        }
        Log.info(DatasourceClientServiceImpl.class, "Create REST URL : " + sb.toString());
        return sb.toString();
    }
}
