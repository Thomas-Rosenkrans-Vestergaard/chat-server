package com.tvestergaard.server.configuration;

import com.tvestergaard.server.ChatServer;

import java.io.File;
import java.net.InetSocketAddress;

/**
 * Class that creates {@link ChatServer} instances from provided configuration files.
 */
public class ChatServerConfigurer
{

    /**
     * The parser used to parse the provided
     */
    private final ConfigurationParser parser;

    /**
     * The object responsible for configuring SSL for the server.
     */
    private final SSLConfigurer sslConfigurer = new SSLConfigurer();

    /**
     * The object responsible for creating data repositories.
     */
    private final PersistenceConfigurer persistenceConfigurer = new PersistenceConfigurer();

    /**
     * Creates a new {@link ChatServerConfigurer}.
     *
     * @param parser The parser used when parsing the provided configuration files.
     */
    public ChatServerConfigurer(ConfigurationParser parser)
    {
        this.parser = parser;
    }

    /**
     * Configures and returns a {@link ChatServer} using the provided configuration file.
     *
     * @param configurationFileLocation The location of the configuration file to configure the {@link ChatServer} from.
     * @return The resulting {@link ChatServer} instance.
     * @throws ConfigurationException When an error occurs while configuring the {@link ChatServer}.
     */
    public ChatServer configure(String configurationFileLocation) throws ConfigurationException
    {
        File configurationFile = new File(configurationFileLocation);
        if (!configurationFile.exists())
            throw new ConfigurationException("The configuration file does not exist.");

        ChatServerConfiguration configuration = parser.parse(configurationFile);

        PersistenceRepositories repositories = persistenceConfigurer.handle(configuration.getPersistenceConfiguration());
        ChatServer chatServer = new ChatServer(new InetSocketAddress(configuration.getHost(),
                configuration.getPort()), repositories);

        SSLConfiguration sslConfiguration = configuration.getSSLConfiguration();
        if (sslConfiguration != null) {
            sslConfigurer.configure(sslConfiguration, chatServer);
        }

        return chatServer;
    }
}
