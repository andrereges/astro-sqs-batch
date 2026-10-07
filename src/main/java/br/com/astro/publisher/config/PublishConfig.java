package br.com.astro.publisher.config;

import br.com.astro.publisher.application.port.in.PublishUseCase;
import br.com.astro.publisher.application.port.out.PublishPort;
import br.com.astro.publisher.application.registry.PublishRegistry;
import br.com.astro.publisher.application.service.PublishService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PublishConfig {

    @Bean
    PublishRegistry publishRegistry(
            List<PublishPort> publishers
    ) {
        return new PublishRegistry(publishers);
    }

    @Bean
    PublishUseCase publishUseCase(
            PublishRegistry registry
    ) {
        return new PublishService(registry);
    }
}
