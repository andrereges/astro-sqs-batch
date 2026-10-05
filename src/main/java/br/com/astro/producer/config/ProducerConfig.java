package br.com.astro.producer.config;

import br.com.astro.producer.application.port.out.MessagePublisherPort;
import br.com.astro.producer.application.port.out.PublishMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

        @Bean
        PublishMessageService publishMessageService(
                MessagePublisherPort publisherPort
        ) {
            return new PublishMessageService(publisherPort);
        }
}
