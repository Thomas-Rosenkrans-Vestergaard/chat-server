package com.tvestergaard.server.communication;

import com.tvestergaard.server.ChatException;

/**
 * Thrown when an incoming message could not be parsed.
 */
public class ParseChatException extends ChatException
{

    public ParseChatException()
    {
        super(1, "Could not parse incoming JSON.");
    }
}
