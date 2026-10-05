package br.com.astro.producer;

import br.com.astro.producer.application.port.out.PublishMessageService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StartupMessagePublisher implements ApplicationRunner {

    private final PublishMessageService publishMessageService;

    public StartupMessagePublisher(
            PublishMessageService publishMessageService
    ) {
        this.publishMessageService = publishMessageService;
    }

    @Override
    public void run(ApplicationArguments args) {
        var payload = new StartupMessage(
                UUID.randomUUID(),
                """
                        {
                            "quantity": 10,
                            "channelCode": 27,
                            "operationType": "NOVO"
                        }
                        """
        );

        publishMessageService.publish(
                "PortalMassa",
                "DISCOVERY",
                payload
        );
    }

    public record StartupMessage(
            UUID id,
            String message
    ) {
    }
}
