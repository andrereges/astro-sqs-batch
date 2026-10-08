package br.com.astro.consumer.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumeConfig {

    private final String url;

    public RabbitMQConsumeConfig(
            @Value("${app.consumer.broker.rabbitmq.url}")
            String url
    ) {
        this.url = url;
    }

//    @Bean
    public ConnectionFactory rabbitConnectionFactory() {

        var factory =
                new com.rabbitmq.client.ConnectionFactory();

        try {
            factory.setUri(url);
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Não foi possível configurar a conexão com RabbitMQ",
                    exception
            );
        }

        return new CachingConnectionFactory(factory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory
    rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory
    ) {

        var factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(
                connectionFactory
        );

        return factory;
    }
}