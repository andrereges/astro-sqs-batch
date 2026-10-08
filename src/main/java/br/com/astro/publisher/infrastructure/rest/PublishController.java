package br.com.astro.publisher.infrastructure.rest;

import br.com.astro.publisher.domain.entity.PublishEnvelope;
import org.springframework.http.ResponseEntity;

public interface PublishController {

    ResponseEntity<PublishEnvelope> publish(
            String broker, String destination, Object content);
}
