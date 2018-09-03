package com.tvestergaard.server.input;

import com.tvestergaard.server.User;
import org.json.JSONObject;

/**
 * Defines a type that handles an incoming message. The {@link MessageReceiverCommand#getMessageType()} method is used to identify
 * the {@link MessageReceiverCommand} that handles an incoming message.
 */
public interface MessageReceiverCommand
{

    /**
     * Returns the identifier of the message type handled by this command.
     *
     * @return The identifier of the message type handled by this command.
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
