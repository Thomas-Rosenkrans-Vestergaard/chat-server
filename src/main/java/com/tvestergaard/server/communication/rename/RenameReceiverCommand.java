package com.tvestergaard.server.communication.rename;

import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.communication.MessageTransmitter;
import com.tvestergaard.server.communication.MissingDataException;
import com.tvestergaard.server.communication.ReceiverCommand;
import org.json.JSONObject;

/**
 * Simple {@link ReceiverCommand} implementation allowing connected users to change their usernames. The result
 * of the operation is a return message to the sender, and if successful a broadcast to the other connected users.
 */
public class RenameReceiverCommand implements ReceiverCommand
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
    public RenameReceiverCommand(MessageTransmitter transmitter, UserRepository users)
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

}
