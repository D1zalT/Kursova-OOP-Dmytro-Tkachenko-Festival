package com.example.festival.mapper;

import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.dto.FestivalRequestDto;
import com.example.festival.dto.FestivalResponseDto;
import com.example.festival.dto.FestivalSummaryDto;
import com.example.festival.entity.Festival;

import java.util.Set;
import java.util.stream.Collectors;

public final class FestivalMapper {

    private FestivalMapper() {
    }

    public static Festival toEntity(FestivalRequestDto dto) {
        Festival festival = new Festival();
        festival.setName(dto.getName());
        festival.setCity(dto.getCity());
        festival.setStartDate(dto.getStartDate());
        festival.setEndDate(dto.getEndDate());
        return festival;
    }

    public static void updateEntity(Festival festival, FestivalRequestDto dto) {
        festival.setName(dto.getName());
        festival.setCity(dto.getCity());
        festival.setStartDate(dto.getStartDate());
        festival.setEndDate(dto.getEndDate());
    }

    public static FestivalResponseDto toResponseDto(Festival festival) {
        Set<ArtistResponseDto> artists = festival.getArtists() == null
                ? Set.of()
                : festival.getArtists().stream().map(ArtistMapper::toResponseDto).collect(Collectors.toSet());
        return new FestivalResponseDto(
                festival.getId(), festival.getName(), festival.getCity(),
                festival.getStartDate(), festival.getEndDate(), artists);
    }

    public static FestivalSummaryDto toSummaryDto(Festival festival) {
        return new FestivalSummaryDto(
                festival.getId(), festival.getName(), festival.getCity(),
                festival.getStartDate(), festival.getEndDate());
    }
}
