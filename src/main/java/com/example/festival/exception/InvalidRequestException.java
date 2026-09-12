package com.example.festival.exception;

/**
 * Кидається, коли запит формально коректний (пройшов Bean Validation),
 * але порушує бізнес-правило (наприклад, дата закінчення фестивалю раніша
 * за дату початку). Обробляється як HTTP 400 Bad Request.
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
