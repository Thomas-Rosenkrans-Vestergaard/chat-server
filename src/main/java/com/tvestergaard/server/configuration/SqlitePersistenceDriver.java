package com.tvestergaard.server.configuration;

import com.tvestergaard.server.persistence.UserRepository;
import com.tvestergaard.server.persistence.sqlite.SqliteUserRepository;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Map;

public class SqlitePersistenceDriver implements PersistenceDriver
{

    @Override public boolean accepts(PersistenceConfiguration configuration)
    {
        return configuration.getDriver().toLowerCase().equals("sqlite");
    }

    @Override public PersistenceRepositories handle(PersistenceConfiguration configuration) throws ConfigurationException
    {
        Map<String, String> options = configuration.getOptions();

        String database = options.get("database");
        if (database == null)
            throw new ConfigurationException("Missing database attribute for sqlite driver.");

        try {
            SQLiteDataSource source = new SQLiteDataSource();
            source.setUrl(String.format("jdbc:sqlite:%s", database));
            Connection connection = source.getConnection();

            return new PersistenceRepositoriesData(
                    new SqliteUserRepository(source)
            );

        } catch (Exception e) {
            throw new ConfigurationException("Exception while connecting the sqlite database.", e);
        }
    }

    private static class PersistenceRepositoriesData implements PersistenceRepositories
    {

        private final UserRepository userRepository;

        public PersistenceRepositoriesData(UserRepository userRepository)
        {
            this.userRepository = userRepository;
        }

        @Override public UserRepository getUserRepository()
        {
            return userRepository;
        }
    }
}
