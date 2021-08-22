package br.com.southsystem.cooperativa.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String mensage) {
        super(mensage);
    }
}
