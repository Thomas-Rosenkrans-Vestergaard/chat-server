package com.tvestergaard.server.output.messages;

import com.tvestergaard.server.User;
import org.json.JSONObject;

/**
 * Notifies clients that a new user has joined.
 */
public class PublicConnectedMessage implements Message
{

    /**
     * The user that just connected.
     */
    private final User user;

    /**
     * Creates a new {@link PublicConnectedMessage}.
     *
     * @param user The user that just connected.
     */
    public PublicConnectedMessage(User user)
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
        return "public-open-message";
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
