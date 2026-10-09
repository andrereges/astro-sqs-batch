package br.com.astro.publisher.domain.exception;

public class DestinationNotFoundException extends RuntimeException {

    public DestinationNotFoundException(String message) {
        super(message);
    }

    public DestinationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
