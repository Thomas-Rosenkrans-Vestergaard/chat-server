package com.tvestergaard.server.input;

import com.tvestergaard.server.GeneralChatException;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.output.MessageTransmitter;
import com.tvestergaard.server.output.messages.Recipients;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link MessageReceiver} where multiple {@link MessageReceiverCommand}s can be registered to handle differing
 * message types.
 */
public class DelegatingMessageReceiver implements MessageReceiver
{

    private final String ATTRIBUTE_TYPE    = "type";
    private final String ATTRIBUTE_PAYLOAD = "payload";

    /**
     * The registered {@link MessageReceiverCommand} instances.
     */
    private final Map<String, MessageReceiverCommand> commands = new HashMap<>();

    /**
     * The object used to send messages to users, used in exceptional cases.
     */
    private final MessageTransmitter transmitter;

    /**
     * The repository storing the connected users.
     */
    private final UserRepository users;

    /**
     * Creates a new {@link DelegatingMessageReceiver}.
     *
     * @param transmitter The object used to send messages to users, used in exceptional cases.
     * @param users       The repository storing the connected users.
     */
    public DelegatingMessageReceiver(MessageTransmitter transmitter, UserRepository users)
    {
        this.transmitter = transmitter;
        this.users = users;
    }

    /**
     * Registers a new {@link MessageReceiverCommand}.
     *
     * @param command The command to register with the object.
     */
    public void register(MessageReceiverCommand command)
    {
        commands.put(command.getMessageType(), command);
    }

    /**
     * Parses the incoming message and delegates its payload to a suitable {@link MessageReceiverCommand}.
     *
     * @param sender  The user who sent the message.
     * @param message The raw message.
     */
    @Override public void handle(User sender, String message)
    {
        try {
            JSONTokener tokener = new JSONTokener(message);
            JSONObject  root    = new JSONObject(tokener);

            MessageReceiverCommand responsiblecommand = commands.get(root.getString(ATTRIBUTE_TYPE));
            if (responsiblecommand == null) {
                transmitter.send(Recipients.toThese(sender), new NoCommandException());
                return;
            }

            responsiblecommand.handle(root.getJSONObject(ATTRIBUTE_PAYLOAD), sender);

        } catch (JSONException e) {
            transmitter.send(Recipients.toThese(sender), new ParseChatException());
        } catch (Exception e) {
            transmitter.send(Recipients.toThese(sender), new GeneralChatException(e));
        }
    }
}
