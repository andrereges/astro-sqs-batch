package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.BrokerEnum;
import br.com.astro.publisher.domain.PublishEnvelope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

@Component
public class SqsPublishAdapter implements PublishPort {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;

    @Value("${app.publisher.sqs.url}")
    private String url;

    public SqsPublishAdapter(
            SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper
    ) {
        this.sqsAsyncClient = sqsAsyncClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.SQS;
    }

    @Override
    public <T> void publish(
            String destination,
            T payload
    ) {
        try {
            PublishEnvelope<T> envelope = PublishEnvelope.of(broker(), destination, payload);
            final String body = objectMapper.writeValueAsString(envelope);

            var request = SendMessageRequest.builder()
                    .queueUrl(getQueueUrl(destination))
                    .messageBody(body)
                    .build();

            sqsAsyncClient.sendMessage(request).join();
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Erro ao publicar mensagem na AWS SQS",
                    exception
            );
        }
    }

    private String getQueueUrl(String destination) {
        return url + "/" + destination;
    }
}
