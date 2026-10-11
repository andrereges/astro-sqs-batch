package br.com.astro.publisher.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "app.publisher.broker.sqs",
        name = "enabled",
        havingValue = "true"
)
public class SqsPublishConfig {

    private final String region;

    public SqsPublishConfig(
            @Value("${spring.cloud.aws.region.static}") String region
    ) {
        this.region = region;
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.of(region))
                .build();
    }
}