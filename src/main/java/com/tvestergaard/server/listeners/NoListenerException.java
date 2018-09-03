package com.tvestergaard.server.listeners;

import com.tvestergaard.server.ChatException;

public class NoListenerException extends ChatException
{

    public NoListenerException()
    {
        super(2, "No listener for that command.");
    }
}
