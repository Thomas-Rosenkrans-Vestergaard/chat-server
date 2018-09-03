package com.tvestergaard.server;

import com.tvestergaard.server.messages.MessageComposer;
import com.tvestergaard.server.messages.OutMessage;
import com.tvestergaard.server.messages.Recipients;

import java.util.List;
import java.util.Set;

public class ConnectedMessageSender implements MessageSender
{

    private final UserRepository  users;
    private final MessageComposer composer;

    public ConnectedMessageSender(UserRepository users, MessageComposer composer)
    {
        this.users = users;
        this.composer = composer;
    }

    @Override public void send(Recipients recipients, OutMessage message)
    {
        Recipients.Mode mode        = recipients.getMode();
        String          textMessage = composer.compose(message);

        if (mode == Recipients.Mode.TO_ALL) {
            sendToAll(textMessage);
            return;
        }

        if (mode == Recipients.Mode.TO_ALL_EXCEPT) {
            sendToAllExcept(textMessage, recipients.getSet());
            return;
        }

        if (mode == Recipients.Mode.TO_THESE) {
            sendToThese(textMessage, recipients.getList());
            return;
        }
    }

    /**
     * Sends some {@link OutMessage} to all the users in chat.
     *
     * @param message The message to send.
     */
    private void sendToAll(String message)
    {
        for (User user : users.collect()) {
            user.getConnection().send(message);
        }
    }


    private void sendToAllExcept(String message, Set<User> exceptions)
    {
        for (User user : users.collect()) {
            if (!exceptions.contains(user)) {
                user.getConnection().send(message);
            }
        }
    }

    private void sendToThese(String message, List<User> recipients)
    {
        for (User user : recipients) {
            user.getConnection().send(message);
        }
    }
}
