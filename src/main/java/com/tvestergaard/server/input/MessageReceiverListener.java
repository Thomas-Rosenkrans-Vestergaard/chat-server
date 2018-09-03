package com.tvestergaard.server.input;

import com.tvestergaard.server.User;
import org.json.JSONObject;

/**
 * Defines a type that handles an incoming message. The {@link MessageReceiverListener#getMessageType()} method is used to identify
 * the {@link MessageReceiverListener} that handles an incoming message.
 */
public interface MessageReceiverListener
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
