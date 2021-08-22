package br.com.southsystem.cooperativa.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensage) {
        super(mensage);
    }
}
