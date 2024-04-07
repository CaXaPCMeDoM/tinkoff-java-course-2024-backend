package edu.java.bot.configuration.kafka;

import edu.java.bot.configuration.kafka.props.KafkaProperties;
import edu.java.bot.web.controller.dto.LinkUpdateRequest;
import jakarta.xml.bind.ValidationException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
@EnableKafka
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConsumerConfig {
    // https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> kafkaListenerContainerFactory(
        KafkaProperties kafkaProperties,
        KafkaTemplate<String, byte[]> kafkaTemplate
    ) {
        ConcurrentKafkaListenerContainerFactory<String, LinkUpdateRequest> containerFactory =
            new ConcurrentKafkaListenerContainerFactory<>();

        containerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
            ConsumerConfig.FETCH_MIN_BYTES_CONFIG, kafkaProperties.getFetchMinBytes(),
            ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName(),
            JsonDeserializer.USE_TYPE_INFO_HEADERS, "false",
            JsonDeserializer.VALUE_DEFAULT_TYPE, LinkUpdateRequest.class,
            JsonDeserializer.KEY_DEFAULT_TYPE, StringDeserializer.class
        )));
        DefaultErrorHandler errorHandler = getDefaultErrorHandler(kafkaTemplate);
        containerFactory.setCommonErrorHandler(errorHandler);

        return containerFactory;
    }

    @NotNull private static DefaultErrorHandler getDefaultErrorHandler(KafkaTemplate<String, byte[]> kafkaTemplate) {
        DeadLetterPublishingRecoverer deadLetterPublishingRecoverer =
            new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (cr, ex) -> {
                    log.error("Ошибка при обработке сообщения. Topic: {}, Partition: {}, Offset: {}",
                        cr.topic(), cr.partition(), cr.offset());
                    return new TopicPartition(cr.topic() + "_dlq", cr.partition());
                });

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(deadLetterPublishingRecoverer);
        errorHandler.addNotRetryableExceptions(ValidationException.class);
        return errorHandler;
    }
}
