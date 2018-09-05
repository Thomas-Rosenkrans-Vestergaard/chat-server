package com.tvestergaard.server.communication.status;

import com.tvestergaard.server.User;
import com.tvestergaard.server.communication.Message;
import org.json.JSONObject;

/**
 * Defines a message that notifies a user when they have connected.
 */
public class ConnectedResponse implements Message
{

    /**
     * The user that connected.
     */
    private final User user;

    /**
     * Creates a new {@link ConnectedResponse}.
     *
     * @param user The user that connected.
     */
    public ConnectedResponse(User user)
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
        return "connected-response";
    }
}
