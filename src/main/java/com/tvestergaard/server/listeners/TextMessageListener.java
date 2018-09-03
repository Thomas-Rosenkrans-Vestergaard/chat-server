package com.tvestergaard.server.listeners;

import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import org.json.JSONObject;

public class TextMessageListener implements Listener
{

    private final UserRepository users;

    public TextMessageListener(UserRepository users)
    {
        this.users = users;
    }

    /**
     * Returns the identifier of the message type handled by this listener.
     *
     * @return The identifier of the message type handled by this listener.
     */
    @Override public String getMessageType()
    {
        return "message";
    }

    /**
     * Performs some action using the provided payload.
     *
     * @param payload The payload received.
     * @param sender  The user who sent the message.
     */
    @Override public void handle(JSONObject payload, User sender)
    {

    }
}
