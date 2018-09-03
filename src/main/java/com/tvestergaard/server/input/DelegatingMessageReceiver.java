package com.tvestergaard.server.input;

import com.tvestergaard.server.GeneralChatException;
import com.tvestergaard.server.User;
import com.tvestergaard.server.UserRepository;
import com.tvestergaard.server.output.messages.Recipients;
import com.tvestergaard.server.output.MessageSender;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link MessageReceiver} where multiple {@link MessageReceiverListener}s can be registered to handle differing
 * message types.
 */
public class DelegatingMessageReceiver implements MessageReceiver
{

    private final String ATTRIBUTE_TYPE    = "type";
    private final String ATTRIBUTE_PAYLOAD = "payload";

    /**
     * The registered {@link MessageReceiverListener} instances.
     */
    private final Map<String, MessageReceiverListener> listeners = new HashMap<>();

    /**
     * The object used to send messages to users, used in exceptional cases.
     */
    private final MessageSender output;

    /**
     * The repository storing the connected users.
     */
    private final UserRepository users;

    /**
     * Creates a new {@link DelegatingMessageReceiver}.
     *
     * @param output The object used to send messages to users, used in exceptional cases.
     * @param users  The repository storing the connected users.
     */
    public DelegatingMessageReceiver(MessageSender output, UserRepository users)
    {
        this.output = output;
        this.users = users;
    }

    /**
     * Registers a new {@link MessageReceiverListener}.
     *
     * @param listener The listener to register with the object.
     */
    public void register(MessageReceiverListener listener)
    {
        listeners.put(listener.getMessageType(), listener);
    }

    /**
     * Parses the incoming message and delegates its payload to a suitable {@link MessageReceiverListener}.
     *
     * @param sender  The user who sent the message.
     * @param message The raw message.
     */
    @Override public void handle(User sender, String message)
    {
        try {
            JSONTokener tokener = new JSONTokener(message);
            JSONObject  root    = new JSONObject(tokener);

            MessageReceiverListener responsibleListener = listeners.get(root.getString(ATTRIBUTE_TYPE));
            if (responsibleListener == null) {
                output.send(Recipients.toThese(sender), new NoListenerException());
                return;
            }

            responsibleListener.handle(root.getJSONObject(ATTRIBUTE_PAYLOAD), sender);

        } catch (JSONException e) {
            output.send(Recipients.toThese(sender), new ParseChatException());
        } catch (Exception e) {
            output.send(Recipients.toThese(sender), new GeneralChatException(e));
        }
    }
}
