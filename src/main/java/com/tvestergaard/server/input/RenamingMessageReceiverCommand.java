package com.tvestergaard.server.input;

import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.output.MessageTransmitter;
import com.tvestergaard.server.output.messages.Message;
import org.json.JSONObject;

/**
 * Simple {@link MessageReceiverCommand} implementation allowing connected users to change their usernames. The result
 * of the operation is a return message to the sender, and if successful a broadcast to the other connected users.
 */
public class RenamingMessageReceiverCommand implements MessageReceiverCommand
{

    /**
     * The object responsible for sending data to connected users.
     */
    private final MessageTransmitter transmitter;

    /**
     * The connected users.
     */
    private final UserRepository users;

    /**
     * Creates a new {@link RenameNotification}.
     *
     * @param transmitter The object responsible for sending data to connected users.
     * @param users       The connected users.
     */
    public RenamingMessageReceiverCommand(MessageTransmitter transmitter, UserRepository users)
    {
        this.transmitter = transmitter;
        this.users = users;
    }

    /**
     * Returns the identifier of the message type handled by this command.
     *
     * @return The identifier of the message type handled by this command.
     */
    @Override public String getMessageType()
    {
        return "rename";
    }

    /**
     * Performs some action using the provided payload.
     *
     * @param payload The payload received.
     * @param sender  The user who sent the message.
     */
    @Override public void handle(JSONObject payload, User sender)
    {
        String newUsername = payload.getString("new-username");

        if (newUsername == null) {
            transmitter.send(sender, new MissingDataException("Missing argument 'new-username'."));
            return;
        }

        // Check that the username is legal
        if (newUsername.length() < 4 || newUsername.length() > 10) {
            transmitter.send(sender, new RenameResponse(null, 2));
            return;
        }

        if (!users.rename(sender, newUsername)) {
            transmitter.send(sender, new RenameResponse(null, 1));
            return;
        }

        transmitter.send(sender, new RenameResponse(newUsername, 0));
    }

    /**
     * The response made to a rename request received by {@link RenamingMessageReceiverCommand}.
     */
    public static class RenameResponse implements Message
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

    /**
     * The notification sent out to connected users, that another user has been renamed.
     */
    public static class RenameNotification implements Message
    {

        /**
         * The updated user instance, with the new username.
         */
        private final User user;

        /**
         * Creates a new {@link RenameNotification}.
         *
         * @param user The updated user instance, with the new username.
         */
        public RenameNotification(User user)
        {
            this.user = user;
        }

        /**
         * Returns the identifier of the message type.
         *
         * @return The identifier of the message type.
         */
        @Override public String getMessageType()
        {
            return "rename-notification";
        }

        /**
         * Adds the payload of the message to the provided {@code JSONObject}.
         *
         * @param payload The {@code JSONObject} to add the message payload to.
         */
        @Override public void addJson(JSONObject payload)
        {
            payload.put("user", user.toJson());
        }
    }
}
