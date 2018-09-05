package com.tvestergaard.server.communication.rename;

import com.tvestergaard.server.User;
import com.tvestergaard.server.communication.Message;
import org.json.JSONObject;

/**
 * The notification sent out to connected users, that another user has been renamed.
 */
public class RenameNotification implements Message
{

    /**
     * The updated user instance, with the new username.
     */
    private final User user;

    /**
     * Creates a new {@link RenameNotification}.
     *
     * @param user The updated user instance, with the new username.
     */
    public RenameNotification(User user)
    {
        this.user = user;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    @Override public String getMessageType()
    {
        return "rename-notification";
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
}
