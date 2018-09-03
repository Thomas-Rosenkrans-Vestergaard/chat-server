package com.tvestergaard.server;

import com.tvestergaard.server.messages.OutMessage;

public abstract class ChatException extends Exception implements OutMessage
{

    abstract int getExceptionId();

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    @Override public String getMessageType()
    {
        return "error";
    }
}
