package br.com.astro.consumer.service.impl;

import br.com.astro.consumer.service.ConsumeService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SqsConsumeServiceImpl implements ConsumeService {

    @Override
    @SqsListener("${app.publisher.sqs.url}/bac-portal-massa-discovery")
    public void consume(String message) {
        System.out.println(message);
    }
}
