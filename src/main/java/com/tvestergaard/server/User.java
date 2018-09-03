package com.tvestergaard.server;

import org.java_websocket.WebSocket;
import org.json.JSONObject;

public class User
{

    /**
     * The unique identifier of the client.
     */
    private final int id;

    /**
     * The clients username. Can by {@code null}.
     */
    private String username;

    /**
     * The connection the client is connected with.
     */
    private final WebSocket connection;

    /**
     * Creates a new {@link User}.
     *
     * @param id         The unique identifier of the client.
     * @param username   The clients username. Can by {@code null}.
     * @param connection The connection the client is connected with.
     */
    public User(int id, String username, WebSocket connection)
    {
        this.id = id;
        this.username = username;
        this.connection = connection;
    }

    public int getId()
    {
        return this.id;
    }

    public String getUsername()
    {
        return this.username;
    }

    public WebSocket getConnection()
    {
        return this.connection;
    }

    /**
     * Returns a Json representation of the {@code User}.
     *
     * @return The Json representation of the {@code User}.
     */
    public String toJson()
    {
        return new JSONObject()
                .put("id", id)
                .put("username", username)
                .toString();
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override public int hashCode()
    {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
