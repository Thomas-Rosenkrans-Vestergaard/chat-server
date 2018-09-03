package com.tvestergaard.server.output.messages;

import org.json.JSONObject;

public interface Message
{

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    String getMessageType();

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    void addJson(JSONObject payload);
}
