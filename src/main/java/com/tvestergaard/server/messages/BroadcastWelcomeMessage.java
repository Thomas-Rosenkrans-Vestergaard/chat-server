package com.tvestergaard.server.messages;

import com.tvestergaard.server.User;
import org.json.JSONObject;

/**
 * Notifies clients that a new user has joined.
 */
public class BroadcastWelcomeMessage implements OutMessage
{

    /**
     * The new user.
     */
    private final User user;

    /**
     * Creates a new {@link BroadcastWelcomeMessage}.
     *
     * @param user The new user.
     */
    public BroadcastWelcomeMessage(User user)
    {
        this.user = user;
    }

    @Override public String getMessageType()
    {
        return "broadcast_welcome_message";
    }

    @Override public void addJson(JSONObject payload)
    {
        payload.put("user", user.toJson());
    }
}
