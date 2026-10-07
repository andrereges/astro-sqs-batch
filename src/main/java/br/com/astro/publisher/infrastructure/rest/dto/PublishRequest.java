package br.com.astro.publisher.infrastructure.rest.dto;

public record PublishRequest(
        String broker,
        String destination,
        Object payload
) { }
