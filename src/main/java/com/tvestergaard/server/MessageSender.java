package com.tvestergaard.server;

import com.tvestergaard.server.messages.OutMessage;
import com.tvestergaard.server.messages.Recipients;

public interface MessageSender
{

    void send(Recipients recipients, OutMessage message);
}
