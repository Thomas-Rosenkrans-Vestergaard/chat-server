package com.tvestergaard.server.data;

import com.tvestergaard.server.User;
import org.jooq.DSLContext;

/**
 * {@link UserRepository} implementation that performs operations upon a Jooq {@code DSLContext}.
 */
public class JooqUserRepository implements UserRepository
{

    /**
     * The {@code DSLContext} to perform operations upon.
     */
    private final DSLContext dslContext;

    /**
     * Creates a new {@link JooqUserRepository}.
     *
     * @param dslContext The {@code DSLContext} to perform operations upon.
     */
    public JooqUserRepository(DSLContext dslContext)
    {
        this.dslContext = dslContext;
    }

    /**
     * Finds the user with the provided id.
     *
     * @param id The id of the user to find and return.
     * @return The user with the provided id or {@code null} if no such user exists.
     * @throws DataAccessException When an exception occurs while accessing the required data.
     */
    @Override public User find(int id) throws DataAccessException
    {
        return null;
    }

    /**
     * Finds the user with the provided username.
     *
     * @param username The username of the user to find and return.
     * @return The user with the provided username or {@code null} if no such user exists.
     * @throws DataAccessException When an exception occurs while accessing the required data.
     */
    @Override public User find(String username) throws DataAccessException
    {
        return null;
    }

    /**
     * Creates a new user.
     *
     * @param username The username of the user to create.
     * @param hash     The hashed password of the user to create.
     * @return The resulting user instance.
     * @throws DataAccessException When an exception occurs while accessing the required data.
     */
    @Override public User create(String username, String hash) throws DataAccessException
    {
        return null;
    }
}
