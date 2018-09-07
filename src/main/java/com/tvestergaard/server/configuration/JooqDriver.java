package com.tvestergaard.server.configuration;

import org.jooq.DSLContext;

/**
 * Interface that transforms {@link JooqConfiguration}s into repositories of data that can be queried.
 */
public interface JooqDriver
{

    /**
     * Checks whether or not the {@link JooqConfiguration}  can be handled by the
     * {@link JooqDriver#handle(JooqConfiguration)} method.
     *
     * @param configuration The {@link JooqConfiguration} that compatibility is checked for.
     * @return {@code true} if this driver can handle the provided {@link JooqConfiguration}, {@code false}
     * otherwise.
     */
    boolean accepts(JooqConfiguration configuration);

    /**
     * Attempts to transform the incoming {@link JooqConfiguration} into a
     * {@code DataSource}.
     *
     * @param configuration The configuration.
     * @return The resulting {@code DataSource}. Must return {@code null} only when this {@link JooqDriver} cannot
     * use the provided configuration, ie. when {@link JooqDriver#accepts(JooqConfiguration)} returns {@code false}.
     * @throws ConfigurationException When an exception occurs while handling the {@code configuration}.
     */
    DSLContext handle(JooqConfiguration configuration) throws ConfigurationException;
}
