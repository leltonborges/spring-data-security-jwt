package br.com.rest.exceptions;

import br.com.rest.security.exceptions.JWTErrorExceptions;
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
        StanderError err = new StanderError(System.currentTimeMillis(), "duplicating key value violates the uniqueness constraint",
                HttpStatus.CONFLICT, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StanderError> psqlException(UserNotFoundException ex, HttpServletRequest request){
        StanderError err = new StanderError(System.currentTimeMillis(), ex.getMessage(),
                HttpStatus.NOT_FOUND, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(JWTErrorExceptions.class)
    public ResponseEntity<StanderError> jwtErrorsExceptions(JWTErrorExceptions ex, HttpServletRequest request){
        StanderError err = new StanderError(System.currentTimeMillis(), ex.getMessage(),
                HttpStatus.BAD_REQUEST, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
    }
}
