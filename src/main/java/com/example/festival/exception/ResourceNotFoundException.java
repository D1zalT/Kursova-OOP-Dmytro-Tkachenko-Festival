package com.example.festival.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forEntity(String entityName, Long id) {
        return new ResourceNotFoundException(entityName + " з id=" + id + " не знайдено");
    }
}
