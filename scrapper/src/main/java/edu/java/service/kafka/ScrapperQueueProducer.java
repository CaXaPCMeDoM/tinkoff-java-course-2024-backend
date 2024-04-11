package edu.java.service.kafka;

import edu.java.configuration.kafka.props.KafkaProperties;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueProducer {
    private final KafkaTemplate<String, LinkClientUpdateRequest> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public void send(LinkClientUpdateRequest message) {
        try {
            log.info("Send message about update with kafka");
            kafkaTemplate.send(kafkaProperties.getTopicName(), message);
        } catch (Exception ex) {
            log.error("Error except from kafka", ex);
        }
    }
}
