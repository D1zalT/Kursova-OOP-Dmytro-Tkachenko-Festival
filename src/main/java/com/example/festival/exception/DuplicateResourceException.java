package com.example.festival.exception;

/**
 * Кидається, коли створювана/оновлювана сутність порушує унікальність
 * (наприклад, Visitor з таким email вже існує). Обробляється як HTTP 409 Conflict.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
