package com.saquero.ordercore;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Integration test requires PostgreSQL on :5434. Run with Docker Compose.")
class SaqueroOrderCoreApplicationTests {

    @Test
    void contextLoads() {
    }
}