package com.tvestergaard.server.communication.status;

import com.tvestergaard.server.User;
import com.tvestergaard.server.communication.Message;
import org.json.JSONObject;

/**
 * Defines a message that is sent to notify connected users, that a user has disconnected.
 */
public class DisconnectedNotification implements Message
{

    /**
     * The user that is disconnecting.
     */
    private final User user;

    /**
     * Creates a new {@link DisconnectedNotification}.
     *
     * @param user The user that is disconnecting.
     */
    public DisconnectedNotification(User user)
    {
        this.user = user;
    }

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    @Override public void addJson(JSONObject payload)
    {
        payload.put("user", user.toJson());
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    @Override public String getMessageType()
    {
        return "disconnected-notification";
    }
}
