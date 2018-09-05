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

        return new ChatServer(new InetSocketAddress(configuration.getHost(), configuration.getPort()));
    }

//    public static void main(String[] args) throws Exception
//    {
//        if (args.length < 3)
//            throw new IllegalArgumentException("Provided arguments host, port, ssl-dir");
//
//        String host   = args[0];
//        int    port   = Integer.parseInt(args[1]);
//        String rsaDir = args[2];
//
//        ChatServer server  = new ChatServer(new InetSocketAddress(host, port));
//        SSLContext context = getSSLContextFromLetsEncrypt();
//        if (context != null) {
//            server.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(context));
//        }
//        server.start();
//        System.out.println(String.format(
//                "ChatServer started on host %s on port %d.",
//                host,
//                port));
//        System.out.println("'exit' to stop");
//
//        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            String in = systemIn.readLine();
//            server.broadcast(in);
//            if (in.equals("exit")) {
//                server.stop(1000);
//                break;
//            }
//        }
//    }
//
//    /**
//     * Method which returns a SSLContext from a Let's encrypt or IllegalArgumentException on error
//     *
//     * @return a valid SSLContext
//     */
//    private static SSLContext getSSLContextFromLetsEncrypt(String rsaDir) throws Exception
//    {
//        SSLContext context;
//        String     pathTo      = rsaDir;
//        String     keyPassword = "";
//        context = SSLContext.getInstance("TLS");
//
//        byte[] certBytes = parseDERFromPEM(Files.readAllBytes(new File(pathTo + File.separator + "cert.pem").toPath()), "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----");
//        byte[] keyBytes  = parseDERFromPEM(Files.readAllBytes(new File(pathTo + File.separator + "privkey.pem").toPath()), "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----");
//
//        X509Certificate cert = generateCertificateFromDER(certBytes);
//        RSAPrivateKey   key  = generatePrivateKeyFromDER(keyBytes);
//
//        KeyStore keystore = KeyStore.getInstance("JKS");
//        keystore.load(null);
//        keystore.setCertificateEntry("cert-alias", cert);
//        keystore.setKeyEntry("key-alias", key, keyPassword.toCharArray(), new Certificate[]{cert});
//
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//        kmf.init(keystore, keyPassword.toCharArray());
//
//        KeyManager[] km = kmf.getKeyManagers();
//
//        context.init(km, null, null);
//
//        return context;
//    }
//
//    protected static byte[] parseDERFromPEM(byte[] pem, String beginDelimiter, String endDelimiter)
//    {
//        String   data   = new String(pem);
//        String[] tokens = data.split(beginDelimiter);
//        tokens = tokens[1].split(endDelimiter);
//        return DatatypeConverter.parseBase64Binary(tokens[0]);
//    }
//
//    protected static RSAPrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException
//    {
//        PKCS8EncodedKeySpec spec    = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory          factory = KeyFactory.getInstance("RSA");
//        return (RSAPrivateKey) factory.generatePrivate(spec);
//    }
//
//    protected static X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException
//    {
//        CertificateFactory factory = CertificateFactory.getInstance("X.509");
//
//        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
//    }
}
