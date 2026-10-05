package br.com.astro.consumer.config;

import br.com.astro.consumer.application.port.in.ConsumeMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

        @Bean
        ConsumeMessageService consumerMessageService() {
            return new ConsumeMessageService();
        }
}
