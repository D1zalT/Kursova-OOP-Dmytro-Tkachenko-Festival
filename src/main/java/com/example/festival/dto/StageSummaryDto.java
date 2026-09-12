package com.example.festival.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Скорочене представлення Stage - використовується в PerformanceResponseDto,
 * щоб не тягнути за собою вкладений Festival із повним списком артистів.
 */
@Schema(description = "Скорочене представлення сцени (майданчика), без вкладеного фестивалю")
public class StageSummaryDto {

    @Schema(description = "Унікальний ідентифікатор сцени", example = "1")
    private Long id;

    @Schema(description = "Назва сцени", example = "Головна сцена")
    private String name;

    @Schema(description = "Місткість сцени (кількість глядачів)", example = "5000")
    private Integer capacity;

    public StageSummaryDto() {
    }

    public StageSummaryDto(Long id, String name, Integer capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
