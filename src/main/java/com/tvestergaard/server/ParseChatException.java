package com.tvestergaard.server;

import org.json.JSONObject;

public class ParseChatException extends ChatException
{

    @Override int getExceptionId()
    {
        return 1;
    }

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    @Override public void addJson(JSONObject payload)
    {
        payload.put("message", "Could not parse incoming JSON.");
    }
}
