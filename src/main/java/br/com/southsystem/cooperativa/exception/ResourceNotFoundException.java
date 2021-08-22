package br.com.southsystem.cooperativa.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensage) {
        super(mensage);
    }
}
