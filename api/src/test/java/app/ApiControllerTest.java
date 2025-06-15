package app;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.assertj.MockMvcTester.MockMvcRequestBuilder;

@Tag("integration")
class ApiControllerTest extends IntegrationTestSupport {


    @Test
    void callApi() {
        mvcTester.get().uri("/version/up");
        MockMvcRequestBuilder accept = mvcTester.get().uri("/now")
                .accept("application/json");
        log.info(accept);
    }

}
