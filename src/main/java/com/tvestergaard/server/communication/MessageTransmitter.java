package com.tvestergaard.server.communication;

import com.tvestergaard.server.User;

/**
 * Defines a type that sends messages to recipients.
 */
public interface MessageTransmitter
{

    /**
     * Sends the provided message to the provided recipients.
     *
     * @param recipients The recipients to send the message to.
     * @param message    The message to send to the recipients.
     */
    void send(Recipients recipients, Message message);

    /**
     * Sends the provided message to the provided recipients.
     *
     * @param user    The user to send the message to.
     * @param message The message to send to the user.
     */
    default void send(User user, Message message)
    {
        send(Recipients.toThese(user), message);
    }
}
