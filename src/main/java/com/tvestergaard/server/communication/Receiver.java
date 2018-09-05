package com.tvestergaard.server.communication;

import com.tvestergaard.server.User;

/**
 * Defines a type that can handle an incoming message.
 */
public interface Receiver
{

    /**
     * Handles an incoming message.
     *
     * @param sender  The user who sent the message.
     * @param message The raw message.
     */
    void handle(User sender, String message);
}
