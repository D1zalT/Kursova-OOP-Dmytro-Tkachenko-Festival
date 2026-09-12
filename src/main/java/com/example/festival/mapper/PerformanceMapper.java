package com.example.festival.mapper;

import com.example.festival.dto.PerformanceRequestDto;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.entity.Performance;

public final class PerformanceMapper {

    private PerformanceMapper() {
    }

    public static Performance toEntity(PerformanceRequestDto dto) {
        Performance performance = new Performance();
        performance.setPerformanceTime(dto.getPerformanceTime());
        performance.setDurationMinutes(dto.getDurationMinutes());
        return performance;
    }

    public static void updateEntity(Performance performance, PerformanceRequestDto dto) {
        performance.setPerformanceTime(dto.getPerformanceTime());
        performance.setDurationMinutes(dto.getDurationMinutes());
    }

    public static PerformanceResponseDto toResponseDto(Performance performance) {
        return new PerformanceResponseDto(
                performance.getId(),
                performance.getPerformanceTime(),
                performance.getDurationMinutes(),
                performance.getArtist() == null ? null : ArtistMapper.toResponseDto(performance.getArtist()),
                performance.getStage() == null ? null : StageMapper.toSummaryDto(performance.getStage()));
    }
}
