package com.gonzik28.super_kassa;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class TestBase {

    public static final PostgreSQLContainer postgreSQLContainer;
    private static final String USER_NAME = "user";
    private static final String USER_PASSWORD = "pass";
    private static final String DB_NAME = "UserBD";
    private static final String IMAGE = "postgres:latest";

    static {
        postgreSQLContainer = new PostgreSQLContainer<>(IMAGE)
                .withUsername(USER_NAME)
                .withPassword(USER_PASSWORD)
                .withDatabaseName(DB_NAME);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    public void before() {
        Flyway flyway = Flyway.configure().dataSource(postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()).load();
        flyway.clean();
        flyway.migrate();
    }
}
