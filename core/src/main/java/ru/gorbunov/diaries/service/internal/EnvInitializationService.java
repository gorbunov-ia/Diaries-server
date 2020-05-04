package ru.gorbunov.diaries.service.internal;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import ru.gorbunov.diaries.config.UserProperties;
import ru.gorbunov.diaries.domain.User;

import java.util.Map;

import static ru.gorbunov.diaries.config.RoleRegistry.ADMIN_ROLE;

/**
 * Service initializes default users.
 *
 * @author Gorbunov.ia
 */
@Service
public class EnvInitializationService implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * Configuration of default users.
     */
    private final UserProperties userProperties;
    /**
     * Service of users.
     */
    private final UserInternalService userInternalService;
    /**
     * Service for creating users.
     */
    private final UserAdminInternalService userAdminService;


    /**
     * @param userProperties      users registry
     * @param userInternalService user service
     * @param userAdminService    user admin service
     */
    public EnvInitializationService(UserProperties userProperties, UserInternalService userInternalService,
                                    UserAdminInternalService userAdminService) {
        this.userProperties = userProperties;
        this.userInternalService = userInternalService;
        this.userAdminService = userAdminService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (Map.Entry<String, String> userAndPassword : userProperties.getInit().entrySet()) {
            createIfAbsent(userAndPassword.getKey(), userAndPassword.getValue());
        }
    }

    /**
     * create new user if absent.
     *
     * @param userName user name of the user
     * @param password password of the user
     */
    private void createIfAbsent(String userName, String password) {
        if (userInternalService.getUserByLogin(userName).isPresent()) {
            return;
        }
        User user = new User();
        user.setLogin(userName);
        user.setPassword(password);
        user.setEmail(userName + "@test.com");
        userAdminService.createUser(user);
        if (userName.equals(userProperties.getAdmin())) {
            userAdminService.addRole(user, ADMIN_ROLE);
        }
    }

}
