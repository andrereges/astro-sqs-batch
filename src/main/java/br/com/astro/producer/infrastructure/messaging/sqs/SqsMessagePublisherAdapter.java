package br.com.astro.producer.infrastructure.messaging.sqs;

import br.com.astro.producer.application.port.out.MessagePublisherPort;
import br.com.astro.producer.domain.messaging.MessageEnvelope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

@Component
public class SqsMessagePublisherAdapter implements MessagePublisherPort {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;

    @Value("${app.messaging.sqs.sqsUrl}")
    private String sqsUrl;

    public SqsMessagePublisherAdapter(
            SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper
    ) {
        this.sqsAsyncClient = sqsAsyncClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> void publish(
            String queueName,
            String type,
            T payload
    ) {
        try {
            var envelope = MessageEnvelope.of(type, payload);

            final String body = objectMapper.writeValueAsString(envelope);

            final String queueUrl = sqsUrl + "/" + queueName;

            var request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(body)
                    .build();

            sqsAsyncClient.sendMessage(request).join();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erro ao publicar mensagem na AWS SQS",
                    e
            );
        }
    }
}
