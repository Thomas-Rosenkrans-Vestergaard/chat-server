package com.tvestergaard.server.configuration;

import com.tvestergaard.server.ChatServer;
import org.java_websocket.server.DefaultSSLWebSocketServerFactory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class SSLConfigurer
{

    /**
     * Configures the server to use SSL with the information in the provided {@code configuration}.
     *
     * @param configuration The configuration used to set up the ssl connection.
     * @param server        The server instance to apply the ssl configuration to.
     * @throws ConfigurationException When an exception occurs while setting up the ssl connection.
     */
    public void configure(SSLConfiguration configuration, ChatServer server) throws ConfigurationException
    {
        try {
            SSLContext context = getSSLContextFromLetsEncrypt(
                    configuration.getCertificateLocation(),
                    configuration.getPrivateKeyLocation(),
                    configuration.getCertificatePassword());
            if (context != null) {
                server.setWebSocketFactory(new DefaultSSLWebSocketServerFactory(context));
            }
        } catch (Exception e) {
            throw new ConfigurationException("Error while setting up ssl on server.", e);
        }
    }


    /**
     * Method which returns a SSLContext from a Let's encrypt or IllegalArgumentException on error
     *
     * @param certificatePath     The path to the certificate file.
     * @param privateKeyPath      The path to the private key file.
     * @param certificatePassword The password to the certificate. Must not be {@code null}.
     * @return a valid SSLContext
     */
    private static SSLContext getSSLContextFromLetsEncrypt(String certificatePath,
                                                           String privateKeyPath,
                                                           String certificatePassword)
            throws Exception
    {
        SSLContext context;
        context = SSLContext.getInstance("TLS");

        byte[] certBytes = parseDERFromPEM(Files.readAllBytes(new File(certificatePath).toPath()),
                "-----BEGIN CERTIFICATE-----",
                "-----END CERTIFICATE-----");
        byte[] keyBytes = parseDERFromPEM(Files.readAllBytes(new File(privateKeyPath).toPath()),
                "-----BEGIN PRIVATE KEY-----",
                "-----END PRIVATE KEY-----");

        X509Certificate cert = generateCertificateFromDER(certBytes);
        RSAPrivateKey   key  = generatePrivateKeyFromDER(keyBytes);

        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(null);
        keystore.setCertificateEntry("cert-alias", cert);
        keystore.setKeyEntry("key-alias", key, certificatePassword.toCharArray(), new Certificate[]{cert});

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keystore, certificatePassword.toCharArray());

        KeyManager[] km = kmf.getKeyManagers();

        context.init(km, null, null);

        return context;
    }

    protected static byte[] parseDERFromPEM(byte[] pem, String beginDelimiter, String endDelimiter)
    {
        String   data   = new String(pem);
        String[] tokens = data.split(beginDelimiter);
        tokens = tokens[1].split(endDelimiter);
        return DatatypeConverter.parseBase64Binary(tokens[0]);
    }

    protected static RSAPrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        PKCS8EncodedKeySpec spec    = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory          factory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) factory.generatePrivate(spec);
    }

    protected static X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException
    {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");

        return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
    }
}
