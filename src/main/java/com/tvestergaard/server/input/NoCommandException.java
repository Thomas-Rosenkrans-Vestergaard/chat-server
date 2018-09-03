package com.tvestergaard.server.input;

import com.tvestergaard.server.ChatException;

/**
 * Thrown when a fitting command could not be found for a provided message type.
 */
public class NoCommandException extends ChatException
{

    public NoCommandException()
    {
        super(2, "Unknown command.");
    }
}
