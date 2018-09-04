package com.tvestergaard.server.input;

import com.tvestergaard.server.GeneralChatException;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.output.MessageTransmitter;
import com.tvestergaard.server.output.messages.Message;
import com.tvestergaard.server.output.messages.Recipients;
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
            transmitter.send(Recipients.toAll(), new TextMessage(sender, message));
        } catch (Exception e) {
            transmitter.send(Recipients.toThese(sender), new GeneralChatException(e));
        }
    }

    /**
     * Represents a message containing a chat message.
     */
    public static class TextMessage implements Message
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
         * Creates a new {@link TextMessage}.
         *
         * @param sender  The client who sent the message.
         * @param message The body of the message.
         */
        public TextMessage(User sender, String message)
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
}
