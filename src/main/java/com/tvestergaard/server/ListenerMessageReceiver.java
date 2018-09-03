package com.tvestergaard.server;

import com.tvestergaard.server.listeners.Listener;
import com.tvestergaard.server.listeners.NoListenerException;
import com.tvestergaard.server.messages.Recipients;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class ListenerMessageReceiver implements MessageReceiver
{

    private final Map<String, Listener> listeners = new HashMap<>();
    private final MessageSender  messageSender;
    private final UserRepository users;

    public ListenerMessageReceiver(MessageSender messageSender, UserRepository users)
    {
        this.messageSender = messageSender;
        this.users = users;
    }

    public void register(Listener listener)
    {
        listeners.put(listener.getMessageType(), listener);
    }

    @Override public void handle(User sender, String message)
    {
        try {
            JSONTokener tokener = new JSONTokener(message);
            JSONObject  root    = new JSONObject(tokener);

            Listener responsibleListener = listeners.get(root.getString("type"));
            if (responsibleListener == null) {
                messageSender.send(Recipients.toThese(sender), new NoListenerException());
                return;
            }

            responsibleListener.handle(root.getJSONObject("payload"), sender);

        } catch (JSONException e) {
            messageSender.send(Recipients.toThese(sender), new ParseChatException());
            return;
        }
    }
}
