package br.com.astro.publisher.infrastructure.rest.handler;

import br.com.astro.publisher.domain.exception.BrokerException;
import br.com.astro.publisher.domain.exception.BrokerNotFoundException;
import br.com.astro.publisher.domain.exception.DestinationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.sqs.model.QueueDoesNotExistException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BrokerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrokerNotFoundException(
            Exception exception
    ) {
        var response = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Broker not found",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(BrokerException.class)
    public ResponseEntity<ErrorResponse> handleBrokerException(
            Exception exception
    ) {
        var response = new ErrorResponse(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error in publication on Broker",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler({
            QueueDoesNotExistException.class,
            DestinationNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleDestinationNotFoundException(
            Exception exception
    ) {
        var response = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Destination not found",
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    public record ErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message
    ) {
    }
}