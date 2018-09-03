package com.tvestergaard.server.messages;

import com.tvestergaard.server.User;
import org.json.JSONObject;

public class GoodbyeMessage implements OutMessage
{

    private final User user;

    public GoodbyeMessage(User user)
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
        return "goodbye_message";
    }
}
