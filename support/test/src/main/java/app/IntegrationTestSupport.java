package app;

import app.config.container.PostgresContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.containers.PostgreSQLContainer;

@Tag("integration")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("resource")
public abstract class IntegrationTestSupport {

    protected static final Logger log = LogManager.getLogger(IntegrationTestSupport.class);

    @TestConfiguration(proxyBeanMethods = false)
    static class TestContainerConfig {
        @Bean
        @ServiceConnection
        public PostgreSQLContainer<?> postgresContainer() {
            var container = PostgresContainer.create();
            container.start();
            return container;
        }
    }

    @Autowired
    protected MockMvcTester mvcTester;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // PostgreSQL 설정
        // Spring Boot Testcontainers Service Connection will auto-configure datasource properties.

        // Redis 사용 시 주석 해제
        // registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        // registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @AfterEach
    void cleanUp() {
        log.info("테스트 데이터 정리 시작");
        // 테스트 간 데이터 정리 로직 추가 가능
        // 예: 테이블 truncate, 캐시 클리어 등
        log.info("테스트 데이터 정리 완료");
    }
}
