package com.tvestergaard.server.input;

import com.tvestergaard.server.ChatException;

/**
 * Thrown when a fitting listener could not be found for a provided message type.
 */
public class NoListenerException extends ChatException
{

    public NoListenerException()
    {
        super(2, "No listener for that command.");
    }
}
