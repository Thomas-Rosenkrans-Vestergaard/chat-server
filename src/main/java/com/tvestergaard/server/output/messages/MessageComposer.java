package com.tvestergaard.server.output.messages;

/**
 * Converts some {@link Message} to a text-based format.
 */
public interface MessageComposer
{

    /**
     * Converts some {@link Message} to a text-based format.
     *
     * @param message The message to compose.
     * @return The resulting text-based encoding.
     */
    public String compose(Message message);
}
