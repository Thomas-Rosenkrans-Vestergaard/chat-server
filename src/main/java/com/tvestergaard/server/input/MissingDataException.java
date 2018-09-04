package com.tvestergaard.server.input;

import com.tvestergaard.server.ChatException;

public class MissingDataException extends ChatException
{

    /**
     * Creates a new {@link ChatException}.
     *
     * @param message The message sent to the user.
     */
    public MissingDataException(String message)
    {
        super(4, message);
    }
}
