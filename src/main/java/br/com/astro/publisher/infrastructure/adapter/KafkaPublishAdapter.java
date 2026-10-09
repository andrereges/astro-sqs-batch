package br.com.astro.publisher.infrastructure.adapter;

import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.domain.entity.BrokerEnum;
import br.com.astro.publisher.domain.entity.PublishEnvelope;
import br.com.astro.publisher.domain.exception.BrokerException;
import br.com.astro.publisher.domain.validator.PublishValidator;
import br.com.astro.publisher.infrastructure.adapter.validator.KafkaDestinationValidatorAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaPublishAdapter implements PublishPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaDestinationValidatorAdapter validator;

    @Override
    public BrokerEnum broker() {
        return BrokerEnum.KAFKA;
    }

    @Override
    public PublishValidator publishValidator() {
        return validator;
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
            throw new BrokerException(
                    "Erro ao publicar mensagem no KAFKA",
                    exception
            );
        }
    }
}