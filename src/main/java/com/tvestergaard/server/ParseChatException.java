package com.tvestergaard.server;

public class ParseChatException extends ChatException
{

    public ParseChatException()
    {
        super(1, "Could not parse incoming JSON.");
    }
}
