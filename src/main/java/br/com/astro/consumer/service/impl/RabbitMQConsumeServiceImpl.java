package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.entity.ServiceDestinationEnum;
import br.com.astro.consumer.handler.DispatchHandler;
import br.com.astro.consumer.service.ConsumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "app.consumer.broker.rabbitmq",
        name = "enabled",
        havingValue = "true"
)
public class RabbitMQConsumeServiceImpl implements ConsumeService {

    private final DispatchHandler batchDispatchHandler;

    @Override
    @RabbitListener(
            queues = "${app.consumer.broker.rabbitmq.destination.discovery}"
    )
    public void consumeDiscovery(String content) {
        log.info("Consume discovery message from RabbitMQ: {}", content);

        batchDispatchHandler.dispatch(ServiceDestinationEnum.DISCOVERY, content);
    }

    @Override
    @RabbitListener(
            queues = "${app.consumer.broker.rabbitmq.destination.purge}"
    )
    public void consumePurge(String content) {
        log.info("Consume purge message from RabbitMQ: {}", content);

        batchDispatchHandler.dispatch(ServiceDestinationEnum.PURGE, content);
    }
}