package com.example.festival.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Уніфікована структура тіла відповіді для будь-якої помилки (4xx/5xx),
 * яку повертає GlobalExceptionHandler.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Уніфікована структура тіла помилки, яку повертає сервер для будь-якого " +
        "збою обробки запиту (400/404/409/500)")
public class ErrorResponse {

    @Schema(description = "Дата й час виникнення помилки", example = "2026-08-25T12:34:56")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP-код статусу помилки", example = "404")
    private int status;

    @Schema(description = "Коротке ім'я типу помилки", example = "Not Found")
    private String error;

    @Schema(description = "Людиночитний опис причини помилки",
            example = "Festival з id=9999 не знайдено")
    private String message;

    @Schema(description = "Шлях запиту, під час обробки якого сталася помилка",
            example = "/festivals/9999")
    private String path;

    @Schema(description = "Мапа помилок валідації полів (заповнюється лише для 400 з " +
            "помилками @Valid): ключ - назва поля, значення - повідомлення про помилку",
            example = "{\"name\": \"Назва фестивалю обов'язкова\"}")
    // Заповнюється тільки для помилок валідації: поле -> повідомлення
    private Map<String, String> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(int status, String error, String message, String path, Map<String, String> validationErrors) {
        this(status, error, message, path);
        this.validationErrors = validationErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
