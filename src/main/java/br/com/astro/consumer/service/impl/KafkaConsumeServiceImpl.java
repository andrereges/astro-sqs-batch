package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.service.ConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumeServiceImpl implements ConsumeService {

    @Override
    @KafkaListener(
            topics = "${app.consumer.broker.kafka.destination}",
            groupId = "${app.consumer.broker.kafka.group-id}"
    )
    public void consume(String content) {
        log.info("Consume message from KAFKA: {}", content);
    }
}