package edu.java.scrapper.db.docker;

import edu.java.scrapper.db.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@RunWith(SpringRunner.class)
public class IntegrationEnvironmentTest extends IntegrationEnvironment {
    @Test
    public void testContainerStarts() {
        assertTrue(POSTGRES.isRunning());
    }
}
