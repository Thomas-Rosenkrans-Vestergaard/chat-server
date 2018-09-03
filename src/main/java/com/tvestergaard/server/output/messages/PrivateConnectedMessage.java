package com.tvestergaard.server.output.messages;

import com.tvestergaard.server.User;
import org.json.JSONObject;

/**
 * Defines a message that notifies a user when they have connected.
 */
public class PrivateConnectedMessage implements Message
{

    /**
     * The user that connected.
     */
    private final User user;

    /**
     * Creates a new {@link PrivateConnectedMessage}.
     *
     * @param user The user that connected.
     */
    public PrivateConnectedMessage(User user)
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
        return "private-connected-message";
    }
}
