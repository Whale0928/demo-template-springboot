package app.config;

import app.config.container.PostgresContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * 테스트 컨테이너 설정 - 필요한 컨테이너만 활성화
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfig {

    // PostgreSQL 사용 시 주석 해제
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        return PostgresContainer.create();
    }

// MySQL 사용 시 주석 해제
//    @Bean
//    @ServiceConnection
//    public MySQLContainer<?> mysqlContainer() {
//        return MysqlContainer.create();
//    }

// Redis 사용 시 주석 해제d
//    @Bean
//    @ServiceConnection(name = "redis")
//    public GenericContainer<?> redisContainer() {
//        return RedisContainer.create();
//    }
}
