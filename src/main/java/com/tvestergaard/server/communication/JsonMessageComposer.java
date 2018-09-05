package com.tvestergaard.server.communication;

import org.json.JSONObject;

import java.time.Instant;

/**
 * Composes Json strings from provided {@link Message} instances.
 */
public class JsonMessageComposer implements MessageComposer
{

    private final static String ATTRIBUTE_TYPE    = "type";
    private final static String ATTRIBUTE_TIME    = "time";
    private final static String ATTRIBUTE_PAYLOAD = "payload";

    /**
     * Composes some Json from the provided {@link Message}.
     *
     * @param message The message to compose.
     * @return The resulting Json.
     */
    public String compose(Message message)
    {
        JSONObject root = new JSONObject();
        root.put(ATTRIBUTE_TYPE, message.getMessageType());
        root.put(ATTRIBUTE_TIME, Instant.now().getEpochSecond());
        JSONObject payload = new JSONObject();
        message.addJson(payload);
        root.put(ATTRIBUTE_PAYLOAD, payload);

        return root.toString();
    }
}
