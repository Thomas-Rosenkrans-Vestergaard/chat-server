package com.tvestergaard.server.configuration;

public class ConfigurationException extends Exception
{

    public ConfigurationException(String message)
    {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
