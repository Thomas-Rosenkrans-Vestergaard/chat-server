package com.tvestergaard.server;

import org.java_websocket.WebSocket;

import java.util.*;

public class UserRepository
{

    /**
     * The next available id that can be assigned to a user.
     */
    private int nextId = 1;

    /**
     * The users in the repository, mapped to their unique id.
     */
    private final Map<Integer, User> users = new HashMap<>();

    /**
     * The usernames currently in use.
     */
    private final Set<String> usedUsernames = new HashSet<>();

    /**
     * Creates and inserts a new user using the provided connection.
     *
     * @param socket The connection of the new user to create.
     * @return The
     */
    public User add(WebSocket socket)
    {
        int    id       = nextId++;
        String username = randomUsername();
        usedUsernames.add(username);
        User user = new User(id, username, socket);
        users.put(id, user);

        return user;
    }

    /**
     * Removes the provided user from the repository.
     *
     * @param user The user to remove from the repository.
     */
    public void remove(User user)
    {
        usedUsernames.remove(user.getUsername());
        users.remove(user.getId());
    }

    /**
     * Returns a {@code Collection} of the users in the repository.
     *
     * @return The {@code Collection} of the users in the repository.
     */
    public Collection<User> collect()
    {
        return Collections.unmodifiableCollection(users.values());
    }

    /**
     * Creates a new random unique username.
     *
     * @return The random unique username.
     */
    public String randomUsername()
    {
        String randomUsername = Long.toHexString(Double.doubleToLongBits(Math.random()));

        return usedUsernames.contains(randomUsername) ? randomUsername() : randomUsername;
    }
}
