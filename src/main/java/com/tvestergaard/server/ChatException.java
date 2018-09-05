package com.tvestergaard.server;

import com.tvestergaard.server.communication.Message;
import org.json.JSONObject;

/**
 * Defines a chat exception. The exception implements {@link Message} so the exception can be sent to users.
 */
public class ChatException extends Exception implements Message
{

    private final static String ATTRIBUTE_ID      = "id";
    private final static String ATTRIBUTE_NAME    = "name";
    private final static String ATTRIBUTE_MESSAGE = "message";

    /**
     * The id of the exception.
     */
    private final int id;

    /**
     * Creates a new {@link ChatException}.
     *
     * @param id The id of the exception.
     */
    public ChatException(int id)
    {
        this.id = id;
    }

    /**
     * Creates a new {@link ChatException}.
     *
     * @param id      The id of the exception.
     * @param message The message sent to the user.
     */
    public ChatException(int id, String message)
    {
        super(message);
        this.id = id;
    }

    /**
     * Returns the id of the chat exception.
     *
     * @return The id of the chat exception.
     */
    public int getId()
    {
        return this.id;
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
        payload.put(ATTRIBUTE_ID, getId());
        payload.put(ATTRIBUTE_NAME, getClass().getSimpleName());
        payload.put(ATTRIBUTE_MESSAGE, getMessage());
    }
}
