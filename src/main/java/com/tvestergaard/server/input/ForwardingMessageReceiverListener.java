package com.tvestergaard.server.input;

import com.tvestergaard.server.GeneralChatException;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.messages.OutTextMessage;
import com.tvestergaard.server.messages.Recipients;
import com.tvestergaard.server.output.MessageSender;
import org.json.JSONObject;

/**
 * Simple {@link MessageReceiverListener} that forwards messages to all the other connected users. Implements basic
 * communication.
 */
public class ForwardingMessageReceiverListener implements MessageReceiverListener
{

    /**
     * The repository containing the connected users.
     */
    private final UserRepository users;

    /**
     * The {@link MessageSender} used to send messages to connected users.
     */
    private final MessageSender output;

    /**
     * Creates a new {@link ForwardingMessageReceiverListener}.
     *
     * @param users  The repository containing the connected users.
     * @param output The {@link MessageSender} used to send messages to connected users.
     */
    public ForwardingMessageReceiverListener(UserRepository users, MessageSender output)
    {
        this.users = users;
        this.output = output;
    }

    /**
     * Returns the identifier of the message type handled by this listener.
     *
     * @return The identifier of the message type handled by this listener.
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
            output.send(Recipients.toAllExcept(sender), new OutTextMessage(sender, message));
        } catch (Exception e) {
            output.send(Recipients.toThese(sender), new GeneralChatException(e));
        }
    }
}
