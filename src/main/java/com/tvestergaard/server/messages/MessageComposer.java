package com.tvestergaard.server.messages;

/**
 * Converts some {@link OutMessage} to a text-based format.
 */
public interface MessageComposer
{

    /**
     * Converts some {@link OutMessage} to a text-based format.
     *
     * @param message The message to compose.
     * @return The resulting text-based encoding.
     */
    public String compose(OutMessage message);
}
