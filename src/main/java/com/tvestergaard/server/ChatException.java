package com.tvestergaard.server;

import com.tvestergaard.server.messages.OutMessage;
import org.json.JSONObject;

public abstract class ChatException extends Exception implements OutMessage
{

    private final int exceptionId;

    public ChatException(int exceptionId)
    {
        this.exceptionId = exceptionId;
    }

    public ChatException(int exceptionId, String message)
    {
        super(message);
        this.exceptionId = exceptionId;
    }

    public int getExceptionId()
    {
        return this.exceptionId;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    @Override public String getMessageType()
    {
        return "exception";
    }

    @Override public void addJson(JSONObject payload)
    {
        payload.put("id", getExceptionId());
        payload.put("name", getClass().getSimpleName());
        payload.put("message", getMessage());
    }
}
