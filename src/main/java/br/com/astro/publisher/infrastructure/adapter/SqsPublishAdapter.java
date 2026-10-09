package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import br.com.astro.publisher.domain.exception.BrokerException;
import br.com.astro.publisher.domain.exception.DestinationNotFoundException;
import br.com.astro.publisher.domain.validator.PublishValidator;
import br.com.astro.publisher.infrastructure.adapter.validator.SqsDestinationValidatorAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.QueueDoesNotExistException;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsPublishAdapter implements PublishPort {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;
    private final SqsDestinationValidatorAdapter sqsDestinationValidatorAdapter;

    @Value("${app.producer.broker.sqs.url}")
    private String url;

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.SQS;
    }

    @Override
    public PublishValidator publishValidator() {
        return sqsDestinationValidatorAdapter;
    }

    @Override
    public PublishEnvelope publish(
            String destination,
            Object content
    ) {
        try {
            PublishEnvelope envelope = PublishEnvelope.of(content);
            final String body = objectMapper.writeValueAsString(envelope);

            var request = SendMessageRequest.builder()
                    .queueUrl(getQueueUrl(destination))
                    .messageBody(body)
                    .build();

            sqsAsyncClient.sendMessage(request).join();

            return envelope;
        } catch (CompletionException exception) {
            Throwable cause = exception.getCause();

            if (cause instanceof QueueDoesNotExistException) {
                log.error("Destination SQS não encontrado: {}", destination, exception);

                throw new DestinationNotFoundException(
                        "Destination not found: " + destination,
                        exception
                );
            }

            log.error("Erro ao publicar mensagem no SQS", exception);

            throw new BrokerException(
                    "Erro ao publicar mensagem na AWS SQS",
                    exception
            );
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new BrokerException(
                    "Erro ao publicar mensagem na AWS SQS",
                    exception
            );
        }
    }

    private String getQueueUrl(String destination) {
        return url + "/" + destination;
    }
}
