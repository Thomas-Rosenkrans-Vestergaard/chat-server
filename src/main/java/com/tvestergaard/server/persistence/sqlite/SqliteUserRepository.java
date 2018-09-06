package com.tvestergaard.server.persistence.sqlite;

import com.tvestergaard.server.User;
import com.tvestergaard.server.persistence.DataAccessException;
import com.tvestergaard.server.persistence.UserRepository;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;

public class SqliteUserRepository implements UserRepository
{

    /**
     * The {@code SQLiteDataSource} to query.
     */
    private final SQLiteDataSource source;

    /**
     * Creates a new {@link SqliteUserRepository}.
     *
     * @param source The {@code SQLiteDataSource} to query.
     * @throws DataAccessException When an exception occurs while accessing the required data, while performing setup.
     */
    public SqliteUserRepository(SQLiteDataSource source) throws DataAccessException
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
