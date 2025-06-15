package app.config.container;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * MySQL 컨테이너 템플릿
 */
public class MysqlContainer {

    public static MySQLContainer<?> create() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.4"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
    }

    public static MySQLContainer<?> createV8() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
    }

    public static MySQLContainer<?> createMariadb() {
        return new MySQLContainer<>(DockerImageName.parse("mariadb:11"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
    }
}
