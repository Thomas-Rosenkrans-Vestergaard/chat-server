package com.tvestergaard.server.messages;

import com.tvestergaard.server.User;
import org.json.JSONObject;

/**
 * Represents a message containing a chat message.
 */
public class OutTextMessage implements OutMessage
{

    private final static String SENDER_ATTRIBUTE  = "sender";
    private final static String MESSAGE_ATTRIBUTE = "message";

    /**
     * The client who sent the message.
     */
    private final User sender;

    /**
     * The body of the message.
     */
    private final String message;

    /**
     * Creates a new {@link OutTextMessage}.
     *
     * @param sender  The client who sent the message.
     * @param message The body of the message.
     */
    public OutTextMessage(User sender, String message)
    {
        this.sender = sender;
        this.message = message;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    @Override public String getMessageType()
    {
        return "message";
    }

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    @Override public void addJson(JSONObject payload)
    {
        payload.put(SENDER_ATTRIBUTE, sender.toJson());
        payload.put(MESSAGE_ATTRIBUTE, message);
    }
}
