package com.tvestergaard.server;

public class GeneralChatException extends ChatException
{

    public GeneralChatException(Throwable e)
    {
        super(0, e.getMessage());
    }
}
