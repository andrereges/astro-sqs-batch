package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.BrokerEnum;
import br.com.astro.publisher.domain.PublishEnvelope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaPublishAdapter implements PublishPort {

    private final ObjectMapper objectMapper;

    @Value("${app.publisher.kafka.url}")
    private String url;

    public KafkaPublishAdapter(
            ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
    }

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.KAFKA;
    }

    @Override
    public <T> void publish(
            String destination,
            T payload
    ) {
        try {
            PublishEnvelope<T> envelope = PublishEnvelope.of(broker(), destination, payload);
            final String body = objectMapper.writeValueAsString(envelope);

            // TODO
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erro ao publicar mensagem na AWS SQS",
                    e
            );
        }
    }
}
