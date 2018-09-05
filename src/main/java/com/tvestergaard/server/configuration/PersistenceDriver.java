package com.tvestergaard.server.configuration;

/**
 * Interface that transforms {@link PersistenceConfiguration}s into repositories of data that can be queried.
 */
public interface PersistenceDriver
{

    /**
     * Checks whether or not the {@link PersistenceConfiguration}  can be handled by the
     * {@link PersistenceDriver#handle(PersistenceConfiguration)} method.
     *
     * @param configuration The {@link PersistenceConfiguration} that compatibility is checked for.
     * @return {@code true} if this driver can handle the provided {@link PersistenceConfiguration}, {@code false}
     * otherwise.
     */
    boolean accepts(PersistenceConfiguration configuration);

    /**
     * Attempts to transform the incoming {@link PersistenceConfiguration} into an instance of
     * {@link PersistenceRepositories}, containing the repositories needed to run the server.
     *
     * @param configuration The configuration.
     * @return The resulting repositories. Must return {@code null} only when this {@link PersistenceDriver} cannot
     * use the provided configuration.
     * @throws ConfigurationException When an exception occurs while handling the {@code configuration}.
     */
    PersistenceRepositories handle(PersistenceConfiguration configuration) throws ConfigurationException;
}
