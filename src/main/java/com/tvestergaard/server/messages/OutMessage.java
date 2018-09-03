package com.tvestergaard.server.messages;

import org.json.JSONObject;

public interface OutMessage extends Message
{

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    void addJson(JSONObject payload);
}
