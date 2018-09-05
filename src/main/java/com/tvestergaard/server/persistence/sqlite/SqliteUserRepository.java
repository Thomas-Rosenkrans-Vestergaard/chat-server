package com.tvestergaard.server.persistence.sqlite;

import com.tvestergaard.server.User;
import com.tvestergaard.server.persistence.DataAccessException;
import com.tvestergaard.server.persistence.UserRepository;
import org.sqlite.SQLiteDataSource;

public class SqliteUserRepository implements UserRepository
{

    private final SQLiteDataSource source;

    public SqliteUserRepository(SQLiteDataSource source)
    {
        this.source = source;
    }

    @Override public User find(int id) throws DataAccessException
    {
        return null;
    }

    @Override public User find(String username) throws DataAccessException
    {
        return null;
    }

    @Override public User create(String username, String hash) throws DataAccessException
    {
        return null;
    }
}
