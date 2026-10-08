package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class RabbitMQPublishAdapter implements PublishPort {

    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPublishAdapter(
            ObjectMapper objectMapper,
            RabbitTemplate rabbitTemplate
    ) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.RABBITMQ;
    }

    @Override
    public PublishEnvelope publish(
            String destination,
            Object content
    ) {
        try {
            PublishEnvelope envelope =
                    PublishEnvelope.of(content);

            String exchange = extractExchange(destination);

            rabbitTemplate.convertAndSend(
                    exchange,
                    destination,
                    envelope
            );

            return envelope;
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Erro ao publicar mensagem no RabbitMQ",
                    exception
            );
        }
    }

    private String extractExchange(String destination) {
        int separator = destination.indexOf('-');

        if (separator <= 0) {
            throw new IllegalArgumentException(
                    "Destination inválido: " + destination
            );
        }

        return destination.substring(0, separator);
    }
}