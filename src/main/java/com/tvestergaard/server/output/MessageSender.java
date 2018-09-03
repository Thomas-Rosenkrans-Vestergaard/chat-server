package com.tvestergaard.server.output;

import com.tvestergaard.server.output.messages.Message;
import com.tvestergaard.server.output.messages.Recipients;

/**
 * Defines a type that sends messages to recipients.
 */
public interface MessageSender
{

    /**
     * Sends the provided message to the provided recipients.
     *
     * @param recipients The recipients to send the message to.
     * @param message    The message to send to the recipients.
     */
    void send(Recipients recipients, Message message);
}
