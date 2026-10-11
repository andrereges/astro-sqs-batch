package br.com.astro.purgebach.controller;

import br.com.astro.purgebach.controller.dto.PurgeBatchDispatchEnvelopeRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/purge-batch")
@Slf4j
public class PurgeBatchController {

    @PostMapping("/start")
    public ResponseEntity<Void> dispatch(
            @Valid @RequestBody PurgeBatchDispatchEnvelopeRequest request) {
        log.info("Starting purge batch dispatch request: {}", request.toString());
        return ResponseEntity.accepted()
                .location(URI.create("/api/v1/purge-batch/start"))
                .build();
    }
}
