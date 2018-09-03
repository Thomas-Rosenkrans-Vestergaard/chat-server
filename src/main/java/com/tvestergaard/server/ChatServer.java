package com.tvestergaard.server;

import com.tvestergaard.server.listeners.TextMessageListener;
import com.tvestergaard.server.messages.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class ChatServer extends WebSocketServer
{

    /**
     * The connected users.
     */
    private final UserRepository users = new UserRepository();

    /**
     * The object responsible for sending messages.
     */
    private final MessageSender sender = new ConnectedMessageSender(users, new JsonMessageComposer());

    /**
     * The listeners that can receive incoming messages.
     */
    private final ListenerMessageReceiver receiver = new ListenerMessageReceiver(sender, users);

    /**
     * Creates a new {@link ChatServer}.
     *
     * @param address The
     */
    public ChatServer(InetSocketAddress address)
    {
        super(address);

        receiver.register(new TextMessageListener(users, sender));
    }

    @Override public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        User user = users.add(conn);
        conn.setAttachment(user);

        sender.send(Recipients.toAllExcept(user), new BroadcastWelcomeMessage(user));
        sender.send(Recipients.toThese(user), new PersonalWelcomeMessage(user));
    }

    @Override public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        User user = conn.getAttachment();
        users.remove(user);
        sender.send(Recipients.toAllExcept(user), new GoodbyeMessage(user));
    }

    @Override public void onMessage(WebSocket conn, String message)
    {
        receiver.handle(conn.getAttachment(), message);
    }

    @Override public void onError(WebSocket conn, Exception ex)
    {

    }

    @Override public void onStart()
    {

    }
}