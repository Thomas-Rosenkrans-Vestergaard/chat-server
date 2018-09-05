package com.tvestergaard.server.configuration;

public interface SSLConfiguration
{

    /**
     * Returns the path to the certificate file.
     *
     * @return The path to the certificate file.
     */
    String getCertificateLocation();

    /**
     * Returns the path to the private key file.
     *
     * @return The path to the private key file.
     */
    String getPrivateKeyLocation();

    /**
     * Returns the password to the certificate.
     *
     * @return The password to the certificate.
     */
    String getCertificatePassword();
}
