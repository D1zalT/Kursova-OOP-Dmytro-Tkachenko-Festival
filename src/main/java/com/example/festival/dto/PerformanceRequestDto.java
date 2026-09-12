package com.example.festival.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class PerformanceRequestDto {

    @NotNull(message = "Час виступу обов'язковий")
    private LocalDateTime performanceTime;

    @NotNull(message = "Тривалість виступу обов'язкова")
    @Positive(message = "Тривалість виступу має бути додатнім числом хвилин")
    private Integer durationMinutes;

    @NotNull(message = "id виконавця обов'язковий")
    private Long artistId;

    @NotNull(message = "id сцени обов'язковий")
    private Long stageId;

    public PerformanceRequestDto() {
    }

    public LocalDateTime getPerformanceTime() {
        return performanceTime;
    }

    public void setPerformanceTime(LocalDateTime performanceTime) {
        this.performanceTime = performanceTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }
}
