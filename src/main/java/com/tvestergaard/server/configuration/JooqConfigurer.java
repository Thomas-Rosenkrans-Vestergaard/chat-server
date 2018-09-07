package com.tvestergaard.server.configuration;

import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;

public class JooqConfigurer
{

    /**
     * The drivers that can handle incoming {@link JooqConfiguration} instances.
     */
    private final List<JooqDriver> drivers = new ArrayList<>();

    public JooqConfigurer()
    {
        drivers.add(new SQLiteJooqDriver());
    }

    public DSLContext handle(JooqConfiguration configuration) throws ConfigurationException
    {
        for (JooqDriver driver : drivers)
            if (driver.accepts(configuration))
                return driver.handle(configuration);

        throw new ConfigurationException(String.format("No jooq driver for identifier '%d'.", configuration.getDriver()));
    }
}
