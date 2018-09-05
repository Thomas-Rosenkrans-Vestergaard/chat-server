package com.tvestergaard.server.configuration;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of the {@link ConfigurationParser} interface, that parses Json configuration files.
 */
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

            String                   host                     = readHost(root);
            int                      port                     = readPort(root);
            SSLConfiguration         sslConfiguration         = readSSLConfiguration(root);
            PersistenceConfiguration persistenceConfiguration = readPersistenceConfiguration(root);

            return new ServerConfigurationData(host, port, sslConfiguration, persistenceConfiguration);

        } catch (IOException e) {
            throw new ConfigurationParserException("Could not read configuration file.", e);
        }
    }

    /**
     * Reads the persistence configuration from the provided root Json configuration object.
     *
     * @param root The root Json configuration object.
     * @return The resulting {@link PersistenceConfiguration}.
     * @throws ConfigurationParserException When the persistence attribute or any required sub-attributes doesn't
     *                                      exist.
     */
    private PersistenceConfiguration readPersistenceConfiguration(JSONObject root) throws ConfigurationParserException
    {
        JSONObject persistence = nullableGetJsonObject(root, "persistence");
        if (persistence == null)
            throw new ConfigurationParserException("Missing or malformed persistence attribute in root object.");

        String     driver  = nullableGetString(persistence, "driver");
        JSONObject options = nullableGetJsonObject(persistence, "options");

        if (driver == null)
            throw new ConfigurationParserException("Missing or malformed driver attribute in root.persistence object.");
        if (options == null)
            throw new ConfigurationParserException("Missing or malformed options attribute in root.persistence object.");

        return new PersistenceConfigurationData(driver, jsonObjectToMap(options));
    }

    /**
     * Reads the SSL configuration information from the provided root Json configuration object.
     *
     * @param root The root Json configuration object.
     * @return The resulting {@link SSLConfiguration}.
     * @throws ConfigurationParserException When the ssl attribute or any required sub-attributes doesn't exist.
     */
    private SSLConfiguration readSSLConfiguration(JSONObject root) throws ConfigurationParserException
    {
        JSONObject ssl = nullableGetJsonObject(root, "ssl");
        if (ssl == null)
            return null;

        String certificatePath = nullableGetString(ssl, "certificatePath");
        String privateKeyPath  = nullableGetString(ssl, "privateKeyPath");
        String certPassword    = nullableGetString(ssl, "certPassword");

        if (certificatePath == null)
            throw new ConfigurationParserException("Missing or malformed certificatePath attribute in root.ssl object.");
        if (privateKeyPath == null)
            throw new ConfigurationParserException("Missing or malformed privateKeyPath attribute in root.ssl object.");

        return new SSLConfigurationData(certificatePath, privateKeyPath, certPassword);
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
        String host = nullableGetString(root, "host");
        if (host == null)
            throw new ConfigurationParserException("Missing or malformed host attribute in root object.");

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

    private String nullableGetString(JSONObject object, String key)
    {
        try {
            return object.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    private JSONObject nullableGetJsonObject(JSONObject object, String key)
    {
        try {
            return object.getJSONObject(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Creates a new {@code Map<String, String>} from the provided {@code JSONObject}.
     *
     * @param options The {@code JSONObject} to transform into a map.
     * @return The resulting map.
     */
    private Map<String, String> jsonObjectToMap(JSONObject options)
    {
        Iterator<String> it = options.keys();

        Map<String, String> result = new HashMap<>();
        while (it.hasNext()) {
            String key   = it.next();
            String value = nullableGetString(options, key);
            if (value == null)
                result.put(key, options.getString(key));
        }

        return result;
    }

    private static class ServerConfigurationData implements ChatServerConfiguration
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

    private static class SSLConfigurationData implements SSLConfiguration
    {

        private final String certificateLocation;
        private final String privateKeyLocation;
        private final String certificatePassword;

        public SSLConfigurationData(String certificateLocation, String privateKeyLocation, String certificatePassword)
        {
            this.certificateLocation = certificateLocation;
            this.privateKeyLocation = privateKeyLocation;
            this.certificatePassword = certificatePassword;
        }

        @Override public String getCertificateLocation()
        {
            return certificateLocation;
        }

        @Override public String getPrivateKeyLocation()
        {
            return privateKeyLocation;
        }

        @Override public String getCertificatePassword()
        {
            return certificatePassword;
        }
    }

    private static class PersistenceConfigurationData implements PersistenceConfiguration
    {

        private final String              driver;
        private final Map<String, String> options;

        public PersistenceConfigurationData(String driver, Map<String, String> options)
        {
            this.driver = driver;
            this.options = options;
        }

        @Override public String getDriver()
        {
            return driver;
        }

        @Override public Map<String, String> getOptions()
        {
            return options;
        }
    }
}
