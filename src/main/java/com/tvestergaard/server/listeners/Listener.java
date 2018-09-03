package com.tvestergaard.server.listeners;

import com.tvestergaard.server.User;
import org.json.JSONObject;

public interface Listener
{

    /**
     * Returns the identifier of the message type handled by this listener.
     *
     * @return The identifier of the message type handled by this listener.
     */
    String getMessageType();

    /**
     * Performs some action using the provided payload.
     *
     * @param payload The payload received.
     * @param sender  The user who sent the message.
     */
    void handle(JSONObject payload, User sender);
}
