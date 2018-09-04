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
        String        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int           n        = alphabet.length();
        StringBuilder builder  = new StringBuilder(6);
        Random        r        = new Random();

        for (int i = 0; i < 6; i++)
            builder.append(alphabet.charAt(r.nextInt(n)));

        String newUsername = builder.toString();

        return usedUsernames.contains(newUsername) ? randomUsername() : newUsername;
    }

    /**
     * Checks if the provided user instance is contained within this repository such that {@code user == search}.
     *
     * @param user The user to check for.
     * @return {@code true} when the provided user is contained within thsi repository, {@code false} otherwise.
     */
    public boolean contains(User user)
    {
        User find = users.get(user.getId());

        return find == null ? false : find == user;
    }


    /**
     * Attempts to rename the provided user to the provided {@code newUsername}.
     *
     * @param user        The user to rename.
     * @param newUsername The new username.
     * @return {@code true} when the user was renamed, {@code false} in all other cases.
     */
    public boolean rename(User user, String newUsername)
    {
        if (!contains(user))
            return false;

        if (usedUsernames.contains(newUsername) && !user.getUsername().equals(newUsername))
            return false;

        synchronized (usedUsernames) {
            String oldUsername = user.getUsername();
            user.setUsername(newUsername);
            usedUsernames.remove(oldUsername);
            usedUsernames.add(newUsername);
        }

        return true;
    }
}
