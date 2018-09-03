package com.tvestergaard.server;

import com.tvestergaard.server.input.DelegatingMessageReceiver;
import com.tvestergaard.server.input.ForwardingMessageReceiverListener;
import com.tvestergaard.server.output.JsonMessageComposer;
import com.tvestergaard.server.output.messages.*;
import com.tvestergaard.server.output.DefaultMessageSender;
import com.tvestergaard.server.output.MessageSender;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
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
    private final MessageSender sender = new DefaultMessageSender(users, new JsonMessageComposer());

    /**
     * The input that can receive incoming messages.
     */
    private final DelegatingMessageReceiver receiver = new DelegatingMessageReceiver(sender, users);

    /**
     * Creates a new {@link ChatServer}.
     *
     * @param address The
     */
    public ChatServer(InetSocketAddress address)
    {
        super(address);

        receiver.register(new ForwardingMessageReceiverListener(users, sender));
    }

    /**
     * Called when a new connection opens.
     *
     * @param conn      The connection that was just opened.
     * @param handshake Additional information.
     */
    @Override public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        User user = users.add(conn);
        conn.setAttachment(user);

        sender.send(Recipients.toAllExcept(user), new PublicConnectedMessage(user));
        sender.send(Recipients.toThese(user), new PrivateConnectedMessage(user));
    }

    /**
     * Called when a connection is closed.
     *
     * @param conn   The connection that is being closed.
     * @param code   The codes can be looked up here: {@link CloseFrame}
     * @param reason Additional information about the closing.
     * @param remote
     **/
    @Override public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        User user = conn.getAttachment();
        users.remove(user);
        sender.send(Recipients.toAllExcept(user), new DisconnectedMessage(user));
    }

    /**
     * Called when the server receives a message.
     *
     * @param conn    The connection the client used to send the message.
     * @param message The body of the message.
     */
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