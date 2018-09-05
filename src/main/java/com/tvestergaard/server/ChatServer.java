package com.tvestergaard.server;

import com.tvestergaard.server.configuration.PersistenceRepositories;
import com.tvestergaard.server.communication.DelegatingMessageReceiver;
import com.tvestergaard.server.communication.message.ForwardReceiverCommand;
import com.tvestergaard.server.communication.rename.RenameReceiverCommand;
import com.tvestergaard.server.communication.DefaultMessageTransmitter;
import com.tvestergaard.server.communication.JsonMessageComposer;
import com.tvestergaard.server.communication.MessageTransmitter;
import com.tvestergaard.server.communication.status.ConnectedNotification;
import com.tvestergaard.server.communication.status.ConnectedResponse;
import com.tvestergaard.server.communication.status.DisconnectedNotification;
import com.tvestergaard.server.communication.Recipients;
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
    private final MessageTransmitter transmitter = new DefaultMessageTransmitter(users, new JsonMessageComposer());

    /**
     * The input that can receive incoming messages.
     */
    private final DelegatingMessageReceiver receiver = new DelegatingMessageReceiver(transmitter, users);

    /**
     * Creates a new {@link ChatServer}.
     *
     * @param address The
     * @param repositories
     */
    public ChatServer(InetSocketAddress address, PersistenceRepositories repositories)
    {
        super(address);
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

        transmitter.send(Recipients.toAllExcept(user), new ConnectedNotification(user));
        transmitter.send(Recipients.toThese(user), new ConnectedResponse(user));
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
        transmitter.send(Recipients.toAllExcept(user), new DisconnectedNotification(user));
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

    /**
     * Registers the receiver commands with the receiver.
     */
    @Override public void onStart()
    {
        receiver.register(new ForwardReceiverCommand(users, transmitter));
        receiver.register(new RenameReceiverCommand(transmitter, users));
    }
}