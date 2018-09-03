package com.tvestergaard.server.messages;

public interface Message
{

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    String getMessageType();
}
