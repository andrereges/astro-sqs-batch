package br.com.astro.consumer.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "app.batch")
public record BatchProperty(
        Map<String, BatchService> services
) {

    public record BatchService(
            String baseUrl,
            String endpoint
    ) {}
}
