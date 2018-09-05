package com.tvestergaard.server.configuration;

public class ConfigurationParserException extends ConfigurationException
{

    public ConfigurationParserException(String message)
    {
        super(message);
    }

    public ConfigurationParserException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
