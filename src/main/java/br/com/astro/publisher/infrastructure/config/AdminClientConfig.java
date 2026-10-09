package br.com.astro.publisher.infrastructure.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AdminClientConfig {

    @Bean(destroyMethod = "close")
    public AdminClient adminClient(
            @Value("${app.producer.broker.kafka.url}") String bootstrapServers) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("bootstrap.servers", bootstrapServers);

        return AdminClient.create(properties);
    }

}
