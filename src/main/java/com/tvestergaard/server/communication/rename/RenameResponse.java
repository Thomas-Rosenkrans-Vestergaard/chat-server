package com.tvestergaard.server.communication.rename;

import com.tvestergaard.server.communication.Message;
import org.json.JSONObject;

/**
 * The response made to a rename request received by {@link RenameReceiverCommand}.
 */
public class RenameResponse implements Message
{

    /**
     * The new username of the user. When the action failed, this value is {@code null}.
     */
    private final String newUsername;

    /**
     * Whether or not the renaming action was successful. Equals 0 when the rename action was successful. Equals
     * 1 when the username was taken. Equals 2 when the username was illegal.
     */
    private final int code;

    /**
     * Creates a new {@link RenameResponse} object.
     *
     * @param newUsername The new username of the user.
     * @param code        The response code.
     */
    public RenameResponse(String newUsername, int code)
    {
        this.newUsername = newUsername;
        this.code = code;
    }

    /**
     * Returns the identifier of the message type.
     *
     * @return The identifier of the message type.
     */
    @Override public String getMessageType()
    {
        return "rename-response";
    }

    /**
     * Adds the payload of the message to the provided {@code JSONObject}.
     *
     * @param payload The {@code JSONObject} to add the message payload to.
     */
    @Override public void addJson(JSONObject payload)
    {
        payload.put("new-username", newUsername);
        payload.put("code", code);
    }
}
