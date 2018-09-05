package com.tvestergaard.server.persistence;

import com.tvestergaard.server.ChatException;

public class PersistenceException extends ChatException
{

    public PersistenceException(String message)
    {
        super(5, message);
    }
}
