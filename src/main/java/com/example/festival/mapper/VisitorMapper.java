package com.example.festival.mapper;

import com.example.festival.dto.FestivalSummaryDto;
import com.example.festival.dto.VisitorRequestDto;
import com.example.festival.dto.VisitorResponseDto;
import com.example.festival.entity.Visitor;

import java.util.Set;
import java.util.stream.Collectors;

public final class VisitorMapper {

    private VisitorMapper() {
    }

    public static Visitor toEntity(VisitorRequestDto dto) {
        Visitor visitor = new Visitor();
        visitor.setFirstName(dto.getFirstName());
        visitor.setLastName(dto.getLastName());
        visitor.setEmail(dto.getEmail());
        return visitor;
    }

    public static void updateEntity(Visitor visitor, VisitorRequestDto dto) {
        visitor.setFirstName(dto.getFirstName());
        visitor.setLastName(dto.getLastName());
        visitor.setEmail(dto.getEmail());
    }

    public static VisitorResponseDto toResponseDto(Visitor visitor) {
        Set<FestivalSummaryDto> festivals = visitor.getFestivals() == null
                ? Set.of()
                : visitor.getFestivals().stream().map(FestivalMapper::toSummaryDto).collect(Collectors.toSet());
        return new VisitorResponseDto(
                visitor.getId(), visitor.getFirstName(), visitor.getLastName(), visitor.getEmail(), festivals);
    }
}
