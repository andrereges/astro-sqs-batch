package br.com.astro.publisher.infrastructure.adapter.validator;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.exception.DestinationNotFoundException;
import br.com.astro.publisher.domain.validator.PublishValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.errors.UnknownTopicOrPartitionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaDestinationValidatorAdapter implements PublishValidator {

    private final AdminClient adminClient;

    @Override
    public void validate(BrokerEnum broker,
                         String destination,
                         Object content) {
        this.validateDestination(destination);
    }

    private void validateDestination(String destination) {
        try {
            log.info("Validating Destination KAFKA");
            adminClient.describeTopics(List.of(destination))
                    .topicNameValues()
                    .get(destination)
                    .get(5, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Validação do tópico interrompida", e);

        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            if (cause instanceof UnknownTopicOrPartitionException) {
                throw new DestinationNotFoundException(destination, cause);
            }

            throw new IllegalStateException(
                    "Erro ao consultar tópico Kafka: " + destination, cause);

        } catch (TimeoutException e) {
            throw new IllegalStateException(
                    "Timeout ao consultar tópico Kafka: " + destination, e);
        }
    }
}
