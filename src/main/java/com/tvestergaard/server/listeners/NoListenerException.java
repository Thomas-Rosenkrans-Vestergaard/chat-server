package com.tvestergaard.server.listeners;

import com.tvestergaard.server.ChatException;
import org.json.JSONObject;

public class NoListenerException extends ChatException
{



    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    @Override public void addJson(JSONObject payload)
    {
        payload.put("message", "No listener for that command.");
    }
}
