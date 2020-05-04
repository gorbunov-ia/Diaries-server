package ru.gorbunov.diaries;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Parent class for all integration tests.
 */
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {BaseIntegrationTest.Initializer.class})
public class BaseIntegrationTest {

    /**
     * Shared test container.
     */
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:12.2");
        POSTGRES_CONTAINER.start();
    }

    /**
     * Initializer of test containers.
     */
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        /**
         * Initialize a test container.
         *
         * @param configurableApplicationContext context
         */
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            of(POSTGRES_CONTAINER).applyTo(configurableApplicationContext.getEnvironment());
        }

        /**
         * @param container postgresql test container
         * @return test properties
         */
        private TestPropertyValues of(PostgreSQLContainer<?> container) {
            return TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            );
        }
    }
}
