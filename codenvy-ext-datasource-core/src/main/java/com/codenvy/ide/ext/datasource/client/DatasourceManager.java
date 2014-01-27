package com.codenvy.ide.ext.datasource.client;

import java.util.Iterator;
import java.util.Set;

import com.codenvy.ide.ext.datasource.shared.DatabaseConfigurationDTO;

public interface DatasourceManager {

    Iterator<DatabaseConfigurationDTO> getDatasources();

    void add(final DatabaseConfigurationDTO configuration);

    public void remove(final DatabaseConfigurationDTO configuration);

    public DatabaseConfigurationDTO getByName(final String name);

    public Set<String> getNames();
}
