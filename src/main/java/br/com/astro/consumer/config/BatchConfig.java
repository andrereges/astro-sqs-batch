package br.com.astro.consumer.config;

import br.com.astro.consumer.property.BatchProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BatchProperty.class)
public class BatchConfig { }