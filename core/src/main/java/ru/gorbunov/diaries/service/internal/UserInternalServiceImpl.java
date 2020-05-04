package ru.gorbunov.diaries.service.internal;

import org.springframework.stereotype.Service;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.UserRepository;
import ru.gorbunov.diaries.security.SecurityUtils;

import java.util.Optional;

/**
 * Implementation of internal service for interaction with user.
 *
 * @author Gorbunov.ia
 */
@Service
public class UserInternalServiceImpl implements UserInternalService {

    /**
     * Repository for Users.
     */
    private final UserRepository userRepository;

    /**
     * Base constructor.
     *
     * @param repository repository for crud operations with db
     */
    public UserInternalServiceImpl(final UserRepository repository) {
        this.userRepository = repository;
    }

    /**
     * {@inheritDoc}
     */
    public Optional<User> getUserByLogin(final String login) {
        return userRepository.findOneByLogin(login);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<User> getUser(final Integer id) {
        return userRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    public Optional<User> getUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
    }

}
