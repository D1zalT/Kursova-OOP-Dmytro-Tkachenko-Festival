package com.example.festival.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Дані про запланований виступ виконавця, які повертаються клієнту")
public class PerformanceResponseDto {

    @Schema(description = "Унікальний ідентифікатор виступу", example = "1")
    private Long id;

    @Schema(description = "Дата й час початку виступу", example = "2026-07-11T20:30:00")
    private LocalDateTime performanceTime;

    @Schema(description = "Тривалість виступу у хвилинах", example = "60")
    private Integer durationMinutes;

    @Schema(description = "Виконавець, який дає цей виступ")
    private ArtistResponseDto artist;

    @Schema(description = "Сцена (майданчик), на якій відбувається виступ")
    private StageSummaryDto stage;

    public PerformanceResponseDto() {
    }

    public PerformanceResponseDto(Long id, LocalDateTime performanceTime, Integer durationMinutes,
                                   ArtistResponseDto artist, StageSummaryDto stage) {
        this.id = id;
        this.performanceTime = performanceTime;
        this.durationMinutes = durationMinutes;
        this.artist = artist;
        this.stage = stage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ArtistResponseDto getArtist() {
        return artist;
    }

    public void setArtist(ArtistResponseDto artist) {
        this.artist = artist;
    }

    public StageSummaryDto getStage() {
        return stage;
    }

    public void setStage(StageSummaryDto stage) {
        this.stage = stage;
    }
}
