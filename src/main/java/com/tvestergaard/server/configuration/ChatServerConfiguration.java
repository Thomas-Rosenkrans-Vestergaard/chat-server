package com.tvestergaard.server.configuration;

/**
 * Represents configuration options for the {@link com.tvestergaard.server.ChatServer}.
 */
public interface ChatServerConfiguration
{

    /**
     * Returns the host the server in running on.
     *
     * @return The host the server in running on.
     */
    String getHost();

    /**
     * Returns the port the server should use.
     *
     * @return The port the server should use.
     */
    int getPort();

    /**
     * Returns the configuration for the SSL connection (WebSocket Secure).
     *
     * @return The configuration for the SSL connection (WebSocket Secure). The server doesn't use SSL when
     * this method returns {@code null}.
     */
    SSLConfiguration getSSLConfiguration();

    /**
     * Returns the configuration for the persistent storage of information, when the server starts and stops.
     *
     * @return the configuration for the persistent storage of information, when the server starts and stops.
     */
    JooqConfiguration getJooqConfiguration();
}
