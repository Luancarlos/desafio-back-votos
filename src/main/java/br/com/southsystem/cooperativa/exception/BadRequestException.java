package br.com.southsystem.cooperativa.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String mensage) {
        super(mensage);
    }
}
