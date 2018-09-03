package com.tvestergaard.server.output;

import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.output.messages.Message;
import com.tvestergaard.server.output.messages.MessageComposer;
import com.tvestergaard.server.output.messages.Recipients;

import java.util.List;
import java.util.Set;

/**
 * The default implementation of the {@link MessageSender} interface.
 */
public class DefaultMessageSender implements MessageSender
{

    /**
     * The repository containing the connected users.
     */
    private final UserRepository users;

    /**
     * The object used to create text-based messages that can be transmitted using websockets.
     */
    private final MessageComposer composer;

    /**
     * Creates a new {@link DefaultMessageSender}.
     *
     * @param users    The repository containing the connected users.
     * @param composer The object used to create text-based messages that can be transmitted using websockets.
     */
    public DefaultMessageSender(UserRepository users, MessageComposer composer)
    {
        this.users = users;
        this.composer = composer;
    }

    /**
     * Sends the provided message to the provided recipients.
     *
     * @param recipients The recipients to send the message to.
     * @param message    The message to send to the recipients.
     */
    @Override public void send(Recipients recipients, Message message)
    {
        Recipients.Group mode        = recipients.getGroup();
        String           textMessage = composer.compose(message);

        if (mode == Recipients.Group.ALL) {
            sendToAll(textMessage);
            return;
        }

        if (mode == Recipients.Group.ALL_EXCEPT) {
            sendToAllExcept(textMessage, recipients.getExceptions());
            return;
        }

        if (mode == Recipients.Group.TO_THESE) {
            sendToThese(textMessage, recipients.getThese());
            return;
        }
    }

    /**
     * Sends the provided {@link Message} to all the connected users.
     *
     * @param message The message to send.
     */
    private void sendToAll(String message)
    {
        for (User user : users.collect()) {
            user.getConnection().send(message);
        }
    }

    /**
     * Sends the provided {@link Message} to all the connection users, except those provided in the {@code Set}.
     *
     * @param message    The message to send.
     * @param exceptions The users that should not receive the message.
     */
    private void sendToAllExcept(String message, Set<User> exceptions)
    {
        for (User user : users.collect()) {
            if (!exceptions.contains(user)) {
                user.getConnection().send(message);
            }
        }
    }

    /**
     * Sends the provided {@link Message} to all the provided {@code recipients}.
     *
     * @param message    The message to send.
     * @param recipients The users that should receive the message.
     */
    private void sendToThese(String message, List<User> recipients)
    {
        for (User user : recipients) {
            user.getConnection().send(message);
        }
    }
}