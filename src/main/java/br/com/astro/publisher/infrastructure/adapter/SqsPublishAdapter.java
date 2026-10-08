package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
public class SqsPublishAdapter implements PublishPort {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;

    @Value("${app.producer.broker.sqs.url}")
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
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
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
