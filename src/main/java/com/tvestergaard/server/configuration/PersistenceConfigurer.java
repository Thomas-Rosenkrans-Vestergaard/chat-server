package com.tvestergaard.server.configuration;

import java.util.ArrayList;
import java.util.List;

public class PersistenceConfigurer
{

    /**
     * The drivers that can handle incoming {@link PersistenceConfiguration} instances.
     */
    private final List<PersistenceDriver> drivers = new ArrayList<>();

    public PersistenceConfigurer()
    {
        drivers.add(new SqlitePersistenceDriver());
    }

    public PersistenceRepositories handle(PersistenceConfiguration configuration) throws ConfigurationException
    {
        for (PersistenceDriver driver : drivers)
            if (driver.accepts(configuration))
                return driver.handle(configuration);

        throw new ConfigurationException(String.format("No driver for identifier '%d'.", configuration.getDriver()));
    }
}
