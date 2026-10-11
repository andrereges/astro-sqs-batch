package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.entity.ServiceDestinationEnum;
import br.com.astro.consumer.handler.DispatchHandler;
import br.com.astro.consumer.service.ConsumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "app.consumer.broker.kafka",
        name = "enabled",
        havingValue = "true"
)
public class KafkaConsumeServiceImpl implements ConsumeService {

    private final DispatchHandler batchDispatchHandler;

    @Override
    @KafkaListener(
            topics = "${app.consumer.broker.kafka.destination.discovery}",
            groupId = "${app.consumer.broker.kafka.groupId}"
    )
    public void consumeDiscovery(String content) {
        log.info("Consume discovery message from KAFKA: {}", content);

        batchDispatchHandler.dispatch(ServiceDestinationEnum.DISCOVERY, content);
    }

    @Override
    @KafkaListener(
            topics = "${app.consumer.broker.kafka.destination.purge}",
            groupId = "${app.consumer.broker.kafka.groupId}"
    )
    public void consumePurge(String content) {
        log.info("Consume purge message from KAFKA: {}", content);

        batchDispatchHandler.dispatch(ServiceDestinationEnum.PURGE, content);
    }
}