package ru.gorbunov.diaries.security;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.exception.UserNotActivatedException;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.service.internal.UserInternalService;

/**
 * Implementation of service for interaction with User Details.
 *
 * @author Gorbunov.ia
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    /**
     * Logger for class.
     */
    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    /**
     * Service for interaction with user.
     */
    private final UserInternalService userInternalService;

    /**
     * Base constructor.
     *
     * @param userInternalService service for interaction with user
     */
    public DomainUserDetailsService(final UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userInternalService.getUserByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActive()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getDescription()))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin
                + " was not found in the database"));
    }

}
