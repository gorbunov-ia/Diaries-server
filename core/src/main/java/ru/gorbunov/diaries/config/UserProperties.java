package ru.gorbunov.diaries.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Default users registry.
 *
 * @author Gorbunov.ia
 */
@ConfigurationProperties(prefix = "app.users")
public class UserProperties {

    /**
     * Default admin username of the app.
     */
    private String admin;

    /**
     * Map <User name, password> of default users.
     */
    private final Map<String, String> init = new HashMap<>();

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public Map<String, String> getInit() {
        return init;
    }
}
