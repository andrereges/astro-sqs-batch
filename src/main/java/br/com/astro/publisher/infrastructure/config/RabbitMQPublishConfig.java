package br.com.astro.publisher.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQPublishConfig {

    private final String url;

    public RabbitMQPublishConfig(
            @Value("${app.producer.broker.rabbitmq.url}") String url
    ) {
        this.url = url;
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        var factory = new com.rabbitmq.client.ConnectionFactory();

        try {
            factory.setUri(url);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new IllegalStateException(
                    "Não foi possível configurar a conexão com RabbitMQ",
                    exception
            );
        }

        return new CachingConnectionFactory(factory);
    }

    @Bean
    public JacksonJsonMessageConverter rabbitMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter messageConverter
    ) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMessageConverter(messageConverter);

        return rabbitTemplate;
    }
}