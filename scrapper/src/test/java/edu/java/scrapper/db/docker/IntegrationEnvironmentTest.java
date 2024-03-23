package edu.java.scrapper.db.docker;

import edu.java.scrapper.db.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
public class IntegrationEnvironmentTest extends IntegrationEnvironment {
    @Test
    public void testContainerStarts() {
        assertTrue(POSTGRES.isRunning());
    }
}
