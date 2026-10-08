package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.service.ConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumeServiceImpl implements ConsumeService {

    @Override
    @RabbitListener(
            queues = "${app.consumer.broker.rabbit-mq.destination}"
    )
    public void consume(String content) {
        log.info("Consume message from RabbitMQ: {}", content);
    }
}