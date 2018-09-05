package com.tvestergaard.server.configuration;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonConfigurationParser implements ConfigurationParser
{

    /**
     * Parses the provided Json configuration file.
     *
     * @param file The Json configuration file to parse.
     * @return The extracted {@link ChatServerConfiguration}.
     * @throws ConfigurationException When an error occurs while parsing the Json configuration file.
     */
    @Override public ChatServerConfiguration parse(File file) throws ConfigurationParserException
    {
        try {
            JSONTokener tokener = new JSONTokener(readFile(file));
            JSONObject  root    = new JSONObject(tokener);

            String host = readHost(root);
            int    port = readPort(root);

            return new ServerConfigurationData(host, port, null, null);

        } catch (IOException e) {
            throw new ConfigurationParserException("Could not read configuration file.", e);
        }
    }

    /**
     * Reads the host attribute from the provided root Json configuration object.
     *
     * @param root The root Json configuration object.
     * @return The host attribute.
     * @throws ConfigurationParserException When the host attribute doesn't exist.
     */
    private String readHost(JSONObject root) throws ConfigurationParserException
    {
        String host = root.getString("host");
        if (host == null)
            throw new ConfigurationParserException("Missing host attribute in root object.");

        return host;
    }

    /**
     * Reads the port attribute from the provided root Json configuration object.
     *
     * @param root The root Json configuration object.
     * @return The port attribute.
     * @throws ConfigurationParserException When the port attribute doesn't exist, or is not in a correct format.
     */
    private int readPort(JSONObject root) throws ConfigurationParserException
    {
        try {
            return root.getInt("port");
        } catch (Exception e) {
            throw new ConfigurationParserException("Missing or malformed port attribute in root object.", e);
        }
    }

    /**
     * Reads the provided file into a string.
     *
     * @param file The file to read from.
     * @return The contents of the provided file.
     */
    private String readFile(File file) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded);
    }

    static class ServerConfigurationData implements ChatServerConfiguration
    {

        private final String                   host;
        private final int                      port;
        private final SSLConfiguration         sslConfiguration;
        private final PersistenceConfiguration persistenceConfiguration;

        public ServerConfigurationData(String host, int port, SSLConfiguration sslConfiguration, PersistenceConfiguration persistenceConfiguration)
        {
            this.host = host;
            this.port = port;
            this.sslConfiguration = sslConfiguration;
            this.persistenceConfiguration = persistenceConfiguration;
        }

        @Override public String getHost()
        {
            return host;
        }

        @Override public int getPort()
        {
            return port;
        }

        @Override public SSLConfiguration getSSLConfiguration()
        {
            return sslConfiguration;
        }

        @Override public PersistenceConfiguration getPersistenceConfiguration()
        {
            return persistenceConfiguration;
        }
    }
}
