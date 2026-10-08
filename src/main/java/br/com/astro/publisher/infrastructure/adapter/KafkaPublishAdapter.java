package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class KafkaPublishAdapter implements PublishPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaPublishAdapter(
            KafkaTemplate<String, String> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.KAFKA;
    }

    @Override
    public PublishEnvelope publish(
            String destination,
            Object content
    ) {
        try {
            PublishEnvelope envelope =
                    PublishEnvelope.of(content);

            String body =
                    objectMapper.writeValueAsString(envelope);

            kafkaTemplate
                .send(destination, body)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(exception.getMessage(), exception);
                    }
                });

            return envelope;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new IllegalStateException(
                    "Erro ao publicar mensagem no KAFKA",
                    exception
            );
        }
    }
}