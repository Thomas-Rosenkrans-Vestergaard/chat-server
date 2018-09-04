package com.tvestergaard.server.input;

import com.tvestergaard.server.GeneralChatException;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.output.MessageTransmitter;
import com.tvestergaard.server.output.messages.Recipients;
import com.tvestergaard.server.output.messages.TextMessage;
import org.json.JSONObject;

/**
 * Simple {@link MessageReceiverCommand} that forwards messages to all the other connected users. Implements basic
 * communication.
 */
public class ForwardingMessageReceiverCommand implements MessageReceiverCommand
{

    /**
     * The repository containing the connected users.
     */
    private final UserRepository users;

    /**
     * The {@link MessageTransmitter} used to send messages to connected users.
     */
    private final MessageTransmitter transmitter;

    /**
     * Creates a new {@link ForwardingMessageReceiverCommand}.
     *
     * @param users       The repository containing the connected users.
     * @param transmitter The {@link MessageTransmitter} used to send messages to connected users.
     */
    public ForwardingMessageReceiverCommand(UserRepository users, MessageTransmitter transmitter)
    {
        this.users = users;
        this.transmitter = transmitter;
    }

    /**
     * Returns the identifier of the message type handled by this command.
     *
     * @return The identifier of the message type handled by this command.
     */
    @Override public String getMessageType()
    {
        return "message";
    }

    /**
     * Performs some action using the provided payload.
     *
     * @param payload The payload received.
     * @param sender  The user who sent the message.
     */
    @Override public void handle(JSONObject payload, User sender)
    {
        try {
            String message = payload.getString("message");
            transmitter.send(Recipients.toAllExcept(sender), new TextMessage(sender, message));
        } catch (Exception e) {
            transmitter.send(Recipients.toThese(sender), new GeneralChatException(e));
        }
    }
}
