package github.thyago.spaceflightnewsintegration.producer.kafka;

import github.thyago.spaceflightnewsintegration.domain.model.ErrorIntegrationMessage;
import github.thyago.spaceflightnewsintegration.notifier.IntegrationErrorNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IntegrationErrorProducer implements IntegrationErrorNotifier {

    private final Logger LOGGER = LoggerFactory.getLogger(IntegrationErrorProducer.class);

    private final KafkaTemplate kafkaTemplate;

    @Value(value = "${integration.error.topic}")
    private String topic;

    public IntegrationErrorProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void notify(ErrorIntegrationMessage message) {
        var messageKey = UUID.randomUUID().toString();
        this.kafkaTemplate.send(topic, messageKey, message);
    }

}
