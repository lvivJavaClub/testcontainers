package ua.lviv.javaclub.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Slf4j
@Testcontainers
public abstract class BaseComposeTest {



    @Container
    private static final DockerComposeContainer ENVIRONMENT =
    new DockerComposeContainer(new File("src/docker/docker-compose.yml"))
            .withExposedService("db", 3306, Wait.forListeningPort())
            .withExposedService("cache", 6379);

    @DynamicPropertySource
    public static void seProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> {
            var jdbcUrl = "jdbc:mysql://" + ENVIRONMENT.getServiceHost("db", 3306) + ":" +
                          ENVIRONMENT.getServicePort("db", 3306) +
                          "/db?serverTimezone=UTC&TC_REUSABLE=true";
            log.info("spring.datasource.url:{}", jdbcUrl);
            return jdbcUrl;
        });

        registry.add("spring.data.redis.host", () -> {
            var host = ENVIRONMENT.getServiceHost("cache", 6379);
            log.info("spring.data.redis.host:{}", host);
            return host;
        });

        registry.add("spring.data.redis.port", () -> {
            var mappedPort = ENVIRONMENT.getServicePort("cache", 6379);
            log.info("spring.data.redis.port:{}", mappedPort);
            return mappedPort;
        });
    }
}
