package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import br.com.astro.publisher.domain.exception.BrokerException;
import br.com.astro.publisher.domain.validator.PublishValidator;
import br.com.astro.publisher.infrastructure.adapter.validator.RabbitMQDestinationValidatorAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQPublishAdapter implements PublishPort {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQDestinationValidatorAdapter rabbitMQDestinationValidator;

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.RABBITMQ;
    }

    @Override
    public PublishValidator publishValidator() {
        return rabbitMQDestinationValidator;
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
            throw new BrokerException(
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