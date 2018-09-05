package com.tvestergaard.server.configuration;

import java.io.File;

/**
 * Parses a provided configuration file.
 */
public interface ConfigurationParser
{

    /**
     * Parses the provided configuration file.
     *
     * @param file The configuration file to parse.
     * @return The extracted {@link ChatServerConfiguration}.
     * @throws ConfigurationException When an error occurs while parsing the configuration file.
     */
    ChatServerConfiguration parse(File file) throws ConfigurationParserException;
}
