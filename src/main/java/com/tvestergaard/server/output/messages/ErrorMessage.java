package com.tvestergaard.server.output.messages;

import org.json.JSONObject;

public class ErrorMessage implements Message
{

    private final Exception exception;

    public ErrorMessage(Exception exception)
    {
        this.exception = exception;
    }

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    @Override public void addJson(JSONObject payload)
    {
        payload.put("exception-name", exception.getClass().getSimpleName());
    }

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
