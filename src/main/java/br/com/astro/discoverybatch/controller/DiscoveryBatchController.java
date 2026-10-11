package br.com.astro.discoverybatch.controller;

import br.com.astro.discoverybatch.controller.dto.DiscoveryBatchDispatchEnvelopeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discovery-batch")
@Slf4j
public class DiscoveryBatchController {

    @PostMapping("/start")
    public ResponseEntity<Void> dispatch(
            @Valid @RequestBody DiscoveryBatchDispatchEnvelopeRequest request) {
        log.info("Starting discovery batch dispatch request: {}", request.toString());

        return ResponseEntity.accepted()
                .location(URI.create("/api/v1/discovery-batch/start"))
                .build();
    }
}
