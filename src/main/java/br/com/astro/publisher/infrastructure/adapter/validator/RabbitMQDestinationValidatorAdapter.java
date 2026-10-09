package br.com.astro.publisher.infrastructure.adapter.validator;

import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.exception.DestinationNotFoundException;
import br.com.astro.publisher.domain.validator.PublishValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQDestinationValidatorAdapter implements PublishValidator {

    private final RabbitAdmin rabbitAdmin;

    @Override
    public void validate(BrokerEnum broker,
                         String destination,
                         Object content) {
        this.validateDestination(destination);
    }

    private void validateDestination(String destination) {
        log.info("Validating destination in RabbitMQ");

        var queueInfo = rabbitAdmin.getQueueInfo(destination);

        if (queueInfo == null) {
            throw new DestinationNotFoundException(destination);
        }
    }
}
