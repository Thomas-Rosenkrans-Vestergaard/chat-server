package com.tvestergaard.server.configuration;

import java.util.Map;

public interface JooqConfiguration
{

    /**
     * Returns the name of the driver to use to persist information.
     *
     * @return The name of the driver to use to persist information.
     */
    String getDriver();

    /**
     * Returns the options map that is provided to the driver specified by {@link JooqConfiguration#getDriver()}.
     *
     * @return The options map.
     */
    Map<String, String> getConnectionOptions();
}
