package com.tvestergaard.server.listeners;

import com.tvestergaard.server.MessageSender;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.messages.OutTextMessage;
import com.tvestergaard.server.messages.Recipients;
import org.json.JSONObject;

public class TextMessageListener implements Listener
{

    private final UserRepository users;
    private final MessageSender  messageSender;

    public TextMessageListener(UserRepository users, MessageSender messageSender)
    {
        this.users = users;
        this.messageSender = messageSender;
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
            messageSender.send(Recipients.toAllExcept(sender), new OutTextMessage(sender, message));
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
