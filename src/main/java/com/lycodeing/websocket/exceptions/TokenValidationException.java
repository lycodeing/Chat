package com.lycodeing.websocket.exceptions;

/**
 * @author xiaotianyu
 */
public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message) {
        super(message);
    }
}
