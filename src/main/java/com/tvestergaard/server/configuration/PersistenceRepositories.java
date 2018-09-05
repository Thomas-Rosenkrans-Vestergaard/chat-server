package com.tvestergaard.server.configuration;

import com.tvestergaard.server.persistence.UserRepository;

public interface PersistenceRepositories
{

    UserRepository getUserRepository();
}
