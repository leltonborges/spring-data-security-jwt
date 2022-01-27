package br.com.rest.exceptions;

import br.com.rest.security.exceptions.JWTErrorExceptions;
import br.com.rest.services.excepitons.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ResponseHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<StanderError> psqlException(Exception ex, HttpServletRequest request){
        StanderError err = new StanderError(System.currentTimeMillis(), ex.getCause().getCause().getMessage(),
                HttpStatus.CONFLICT, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StanderError> psqlException(UserNotFoundException ex, HttpServletRequest request){

        StanderError err = new StanderError(System.currentTimeMillis(), ex.getMessage(),
                HttpStatus.BAD_REQUEST, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(JWTErrorExceptions.class)
    public ResponseEntity<StanderError> jwtErrorsExceptions(JWTErrorExceptions ex, HttpServletRequest request){
        StanderError err = new StanderError(System.currentTimeMillis(), ex.getMessage(),
                HttpStatus.BAD_REQUEST, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(err);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        StanderError err = new StanderError(System.currentTimeMillis(), ex.getMessage(),
                status, request.getContextPath());

        return ResponseEntity.status(status).body(err);
//        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
