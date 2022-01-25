package br.com.rest.exceptions;

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
}
