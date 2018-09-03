package com.tvestergaard.server;

public interface MessageReceiver
{

    void handle(User sender, String message);
}
