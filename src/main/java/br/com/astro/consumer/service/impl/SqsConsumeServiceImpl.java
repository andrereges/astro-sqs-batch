package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.service.ConsumeService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsConsumeServiceImpl implements ConsumeService {

    @Override
    @SqsListener("${app.consumer.broker.sqs.url}/${app.consumer.broker.sqs.destination}")
    public void consume(String content) {
        log.info("Consume message from AWS SQS: {}", content);
    }
}
