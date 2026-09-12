package com.example.festival.mapper;

import com.example.festival.dto.StageRequestDto;
import com.example.festival.dto.StageResponseDto;
import com.example.festival.dto.StageSummaryDto;
import com.example.festival.entity.Stage;

public final class StageMapper {

    private StageMapper() {
    }

    public static Stage toEntity(StageRequestDto dto) {
        Stage stage = new Stage();
        stage.setName(dto.getName());
        stage.setCapacity(dto.getCapacity());
        return stage;
    }

    public static void updateEntity(Stage stage, StageRequestDto dto) {
        stage.setName(dto.getName());
        stage.setCapacity(dto.getCapacity());
    }

    public static StageResponseDto toResponseDto(Stage stage) {
        return new StageResponseDto(
                stage.getId(), stage.getName(), stage.getCapacity(),
                stage.getFestival() == null ? null : FestivalMapper.toSummaryDto(stage.getFestival()));
    }

    public static StageSummaryDto toSummaryDto(Stage stage) {
        return new StageSummaryDto(stage.getId(), stage.getName(), stage.getCapacity());
    }
}
