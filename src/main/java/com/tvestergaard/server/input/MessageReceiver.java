package com.tvestergaard.server.input;

import com.tvestergaard.server.User;

/**
 * Defines a type that can handle an incoming message.
 */
public interface MessageReceiver
{

    /**
     * Handles an incoming message.
     *
     * @param sender  The user who sent the message.
     * @param message The raw message.
     */
    void handle(User sender, String message);
}
