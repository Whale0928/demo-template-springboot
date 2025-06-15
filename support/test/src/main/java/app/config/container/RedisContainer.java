package app.config.container;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Redis 컨테이너 템플릿
 */
public class RedisContainer {

    public static GenericContainer<?> create() {
        return new GenericContainer<>(DockerImageName.parse("redis:7-alpine"))
                .withExposedPorts(6379)
                .withReuse(true);
    }

    public static GenericContainer<?> createV6() {
        return new GenericContainer<>(DockerImageName.parse("redis:6-alpine"))
                .withExposedPorts(6379)
                .withReuse(true);
    }
}
