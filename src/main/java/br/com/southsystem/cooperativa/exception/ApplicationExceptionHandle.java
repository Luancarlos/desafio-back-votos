package br.com.southsystem.cooperativa.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandle extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse response = new ErrorResponse(
            ex.getMessage(),
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            request.getDescription(false)
        );
  
        return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
    }

}