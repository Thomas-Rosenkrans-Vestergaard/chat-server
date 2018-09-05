package com.tvestergaard.server.persistence;

import com.tvestergaard.server.User;

public interface UserRepository
{

    /**
     * Finds the user with the provided id.
     *
     * @param id The id of the user to find and return.
     * @return The user with the provided id or {@code null} if no such user exists.
     * @throws DataAccessException When an exception occurs while accessing the required data.
     */
    User find(int id) throws DataAccessException;

    /**
     * Finds the user with the provided username.
     *
     * @param username The username of the user to find and return.
     * @return The user with the provided username or {@code null} if no such user exists.
     * @throws DataAccessException When an exception occurs while accessing the required data.
     */
    User find(String username) throws DataAccessException;

    /**
     * Creates a new user.
     *
     * @param username The username of the user to create.
     * @param hash     The hashed password of the user to create.
     * @return The resulting user instance.
     * @throws DataAccessException When an exception occurs while accessing the required data.
     */
    User create(String username, String hash) throws DataAccessException;
}
