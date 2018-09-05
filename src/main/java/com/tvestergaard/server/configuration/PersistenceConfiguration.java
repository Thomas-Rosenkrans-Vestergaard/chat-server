package com.tvestergaard.server.configuration;

import java.util.Map;

public interface PersistenceConfiguration
{

    /**
     * Returns the name of the driver to use to persist information.
     *
     * @return The name of the driver to use to persist information.
     */
    String getDriver();

    /**
     * Returns the options map that is provided to the driver specified by {@link PersistenceConfiguration#getDriver()}.
     *
     * @return The options map.
     */
    Map<String, String> getOptions();
}
