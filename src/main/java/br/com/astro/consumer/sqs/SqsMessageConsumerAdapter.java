package br.com.astro.consumer.sqs;

import br.com.astro.consumer.application.port.in.ConsumeMessageService;
import br.com.astro.consumer.application.port.in.ConsumeMessageUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SqsMessageConsumerAdapter {

    private final ConsumeMessageService consumeMessageService;

    @SqsListener("${app.messaging.sqs.sqsUrl}/PortalMassa")
    public void consume(String message) {
        consumeMessageService.execute(message);
    }
}
