package ru.gorbunov.diaries.service.internal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gorbunov.diaries.domain.Role;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.RoleRepository;
import ru.gorbunov.diaries.repository.UserRepository;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for creating users.
 *
 * @author Gorbunov.ia
 */
@Service
@Transactional
public class UserAdminInternalService {

    /**
     * Repository for Users.
     */
    private final UserRepository userRepository;
    /**
     * Repository for Roles.
     */
    private final RoleRepository roleRepository;
    /**
     * Password encoder.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * @param userRepository  userRepository for crud operations with db
     * @param roleRepository  roleRepository for crud operations with db
     * @param passwordEncoder encoder for user passwords
     */
    public UserAdminInternalService(UserRepository userRepository, RoleRepository roleRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method creates a new user.
     *
     * @param user user object
     * @return new user
     */
    public User createUser(@Valid User user) {
        if (user == null) {
            throw new NullPointerException("New user object should not be null");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("New user object should not have id");
        }
        Set<Integer> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        if (!roleIds.isEmpty() && doRolesExist(roleIds)) {
            throw new IllegalArgumentException("New user object has unknown role");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Check if the roles exist.
     *
     * @param roleIds id of roles
     * @return {@code true} if the roles exists, {@code false} otherwise
     */
    private boolean doRolesExist(Set<Integer> roleIds) {
        return !roleIds.contains(null) && roleIds.size() == roleRepository.findAllById(roleIds).size();
    }

    /**
     * Add the role to the user.
     *
     * @param user            user
     * @param roleDescription description of the role
     * @return the user object
     */
    public User addRole(User user, String roleDescription) {
        if (user == null) {
            throw new NullPointerException("User object should not be null");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("User object should have id");
        }
        final Role role = roleRepository.findByDescription(roleDescription);
        if (role == null) {
            throw new IllegalArgumentException("Role " + roleDescription + " is not found");
        }
        user.addRole(role);
        return userRepository.save(user);
    }
}
