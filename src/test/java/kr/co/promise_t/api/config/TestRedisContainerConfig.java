package kr.co.promise_t.api.config;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("test")
public class TestRedisContainerConfig {
    private static final int TEST_REDIS_PORT = 6380;

    @Container
    private static final GenericContainer<?> redisContainer =
            new GenericContainer<>("redis:7.0.12")
                    .withExposedPorts(TEST_REDIS_PORT)
                    .withReuse(true);

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add(
                "spring.data.redis.port", () -> redisContainer.getMappedPort(TEST_REDIS_PORT).toString());
    }
}
