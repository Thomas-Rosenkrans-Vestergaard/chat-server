package com.tvestergaard.server.persistence;

public class DataAccessException extends PersistenceException
{
    public DataAccessException(String message)
    {
        super(message);
    }
}
