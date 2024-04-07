package edu.java.bot.configuration.kafka.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "kafka", ignoreUnknownFields = false)
public class KafkaProperties {
    private String bootstrapServers;
    private Integer fetchMinBytes;
    private String groupId;
}
