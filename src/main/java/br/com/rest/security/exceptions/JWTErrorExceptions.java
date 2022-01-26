package br.com.rest.security.exceptions;

import java.io.Serializable;

public class JWTErrorExceptions extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 4186858151953899302L;

    public JWTErrorExceptions(String message) {
        super(message);
    }

    public JWTErrorExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
