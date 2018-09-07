package com.tvestergaard.server.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.sqlite.SQLiteDataSource;

import java.util.Map;

/**
 * Transforms an {@link JooqConfiguration} into a {@link javax.sql.DataSource}.
 */
public class SQLiteJooqDriver implements JooqDriver
{

    /**
     * Checks whether or not the {@link JooqConfiguration}  can be handled by this driver. This condition is true
     * when the provided driver is {@code sqlite} (case insensitive).
     *
     * @param configuration The {@link JooqConfiguration} that compatibility is checked for.
     * @return {@code true} if this driver can handle the provided {@link JooqConfiguration}, {@code false}
     * otherwise.
     */
    @Override public boolean accepts(JooqConfiguration configuration)
    {
        return configuration.getDriver().toLowerCase().equals("sqlite");
    }

    @Override
    public DSLContext handle(JooqConfiguration configuration) throws ConfigurationException
    {
        Map<String, String> options = configuration.getConnectionOptions();

        String database = options.get("database");
        if (database == null)
            throw new ConfigurationException("Missing database attribute for sqlite driver.");

        try {
            SQLiteDataSource source = new SQLiteDataSource();
            source.setUrl(String.format("jdbc:sqlite:%s", database));
            return DSL.using(source, SQLDialect.SQLITE);

        } catch (Exception e) {
            throw new ConfigurationException("Exception while connecting the sqlite database.", e);
        }
    }
}
