package br.com.mateus.taskorganizer.infra.exception;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException() {}

    public EntityNotFoundException(String message) {
        super(message);
    }
}