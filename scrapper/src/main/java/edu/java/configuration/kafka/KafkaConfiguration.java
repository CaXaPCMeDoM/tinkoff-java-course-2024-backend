package edu.java.configuration.kafka;

import edu.java.configuration.kafka.props.KafkaProperties;
import edu.java.internal.client.dto.LinkClientUpdateRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({KafkaProperties.class})
public class KafkaConfiguration {
    //https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html
    @Bean
    public KafkaTemplate<String, LinkClientUpdateRequest> stringMessageKafkaTemplate(
        ProducerFactory<String, LinkClientUpdateRequest> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    private Map<String, Object> senderProps(KafkaProperties kafkaProperties) {
        return Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
            ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcksMode()
        );
    }

    @Bean
    public ProducerFactory<String, LinkClientUpdateRequest> producerFactory(KafkaProperties kafkaProperties) {
        return new DefaultKafkaProducerFactory<>(senderProps(kafkaProperties));
    }
}
