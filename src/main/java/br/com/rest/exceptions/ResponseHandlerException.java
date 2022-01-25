package br.com.rest.exceptions;

import br.com.rest.services.excepitons.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseHandlerException {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StanderError> psqlException(DataIntegrityViolationException ex, HttpServletRequest request){
        StanderError err = new StanderError("Duplicate value key", "duplicating key value violates the uniqueness constraint",
                HttpStatus.CONFLICT, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StanderError> psqlException(UserNotFoundException ex, HttpServletRequest request){
        StanderError err = new StanderError("Not found", ex.getMessage(),
                HttpStatus.NOT_FOUND, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}
