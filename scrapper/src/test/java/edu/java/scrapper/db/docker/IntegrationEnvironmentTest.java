package edu.java.scrapper.db.docker;

import edu.java.scrapper.db.IntegrationEnvironment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class IntegrationEnvironmentTest extends IntegrationEnvironment {
    @Test
    public void testContainerStarts() {
        assertTrue(POSTGRES.isRunning());
    }
}
