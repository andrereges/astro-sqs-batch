package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.entity.ServiceDestinationEnum;
import br.com.astro.consumer.handler.DispatchHandler;
import br.com.astro.consumer.service.ConsumeService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "app.consumer.broker.sqs",
        name = "enabled",
        havingValue = "true"
)
public class SqsConsumeServiceImpl implements ConsumeService {

    private final DispatchHandler batchDispatchHandler;

    @Override
    @SqsListener("${app.consumer.broker.sqs.url}/${app.consumer.broker.sqs.destination.discovery}")
    public void consumeDiscovery(String content) {
        log.info("Consume discovery message from AWS SQS: {}", content);

        batchDispatchHandler.dispatch(ServiceDestinationEnum.DISCOVERY, content);
    }

    @Override
    @SqsListener("${app.consumer.broker.sqs.url}/${app.consumer.broker.sqs.destination.purge}")
    public void consumePurge(String content) {
        log.info("Consume purge message from AWS SQS: {}", content);

        batchDispatchHandler.dispatch(ServiceDestinationEnum.PURGE, content);
    }
}
