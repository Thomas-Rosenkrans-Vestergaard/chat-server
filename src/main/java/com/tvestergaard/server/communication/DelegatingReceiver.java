package com.tvestergaard.server.communication;

import com.tvestergaard.server.GeneralChatException;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link Receiver} where multiple {@link ReceiverCommand}s can be registered to handle differing
 * message types.
 */
public class DelegatingReceiver implements Receiver
{

    private final String ATTRIBUTE_TYPE    = "type";
    private final String ATTRIBUTE_PAYLOAD = "payload";

    /**
     * The registered {@link ReceiverCommand} instances.
     */
    private final Map<String, ReceiverCommand> commands = new HashMap<>();

    /**
     * The object used to send messages to users, used in exceptional cases.
     */
    private final MessageTransmitter transmitter;

    /**
     * The repository storing the connected users.
     */
    private final UserRepository users;

    /**
     * Creates a new {@link DelegatingReceiver}.
     *
     * @param transmitter The object used to send messages to users, used in exceptional cases.
     * @param users       The repository storing the connected users.
     */
    public DelegatingReceiver(MessageTransmitter transmitter, UserRepository users)
    {
        this.transmitter = transmitter;
        this.users = users;
    }

    /**
     * Registers a new {@link ReceiverCommand}.
     *
     * @param command The command to register with the object.
     */
    public void register(ReceiverCommand command)
    {
        commands.put(command.getMessageType(), command);
    }

    /**
     * Parses the incoming message and delegates its payload to a suitable {@link ReceiverCommand}.
     *
     * @param sender  The user who sent the message.
     * @param message The raw message.
     */
    @Override public void handle(User sender, String message)
    {
        try {
            JSONTokener tokener = new JSONTokener(message);
            JSONObject  root    = new JSONObject(tokener);

            ReceiverCommand responsibleCommand = commands.get(root.getString(ATTRIBUTE_TYPE));
            if (responsibleCommand == null) {
                transmitter.send(Recipients.toThese(sender), new NoCommandException());
                return;
            }

            responsibleCommand.handle(root.getJSONObject(ATTRIBUTE_PAYLOAD), sender);

        } catch (JSONException e) {
            transmitter.send(Recipients.toThese(sender), new ParseChatException());
        } catch (Exception e) {
            transmitter.send(Recipients.toThese(sender), new GeneralChatException(e));
        }
    }
}
