package br.com.astro.publisher.infrastructure.rest;

import br.com.astro.publisher.infrastructure.rest.dto.PublishRequest;
import br.com.astro.publisher.infrastructure.rest.dto.PublishResponse;
import org.springframework.http.ResponseEntity;

public interface PublishController {

    ResponseEntity<PublishResponse> publish(PublishRequest request);
}
