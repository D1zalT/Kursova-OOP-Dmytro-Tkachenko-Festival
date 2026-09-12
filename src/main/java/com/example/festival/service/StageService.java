package com.example.festival.service;

import com.example.festival.dto.FestivalSummaryDto;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.dto.StageRequestDto;
import com.example.festival.dto.StageResponseDto;
import com.example.festival.entity.Festival;
import com.example.festival.entity.Stage;
import com.example.festival.exception.ResourceNotFoundException;
import com.example.festival.mapper.FestivalMapper;
import com.example.festival.mapper.PerformanceMapper;
import com.example.festival.mapper.StageMapper;
import com.example.festival.repository.FestivalRepository;
import com.example.festival.repository.PerformanceRepository;
import com.example.festival.repository.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StageService {

    private final StageRepository stageRepository;
    private final FestivalRepository festivalRepository;
    private final PerformanceRepository performanceRepository;

    public StageService(StageRepository stageRepository, FestivalRepository festivalRepository,
                         PerformanceRepository performanceRepository) {
        this.stageRepository = stageRepository;
        this.festivalRepository = festivalRepository;
        this.performanceRepository = performanceRepository;
    }

    public List<StageResponseDto> getAll() {
        return stageRepository.findAll().stream().map(StageMapper::toResponseDto).collect(Collectors.toList());
    }

    public StageResponseDto getById(Long id) {
        return StageMapper.toResponseDto(getEntityById(id));
    }

    public StageResponseDto create(StageRequestDto dto) {
        Stage stage = StageMapper.toEntity(dto);
        stage.setFestival(resolveFestival(dto.getFestivalId()));
        return StageMapper.toResponseDto(stageRepository.save(stage));
    }

    public StageResponseDto update(Long id, StageRequestDto dto) {
        Stage existing = getEntityById(id);
        StageMapper.updateEntity(existing, dto);
        if (dto.getFestivalId() != null) {
            existing.setFestival(resolveFestival(dto.getFestivalId()));
        }
        return StageMapper.toResponseDto(stageRepository.save(existing));
    }

    public void delete(Long id) {
        Stage existing = getEntityById(id);
        stageRepository.delete(existing);
    }

    // GET /stages/{id}/performances
    public List<PerformanceResponseDto> getPerformancesOfStage(Long id) {
        getEntityById(id);
        return performanceRepository.findByStageId(id).stream()
                .map(PerformanceMapper::toResponseDto).collect(Collectors.toList());
    }

    // GET /stages/{id}/festival
    public FestivalSummaryDto getFestivalOfStage(Long id) {
        Festival festival = getEntityById(id).getFestival();
        if (festival == null) {
            throw new ResourceNotFoundException("Для сцени з id=" + id + " не задано фестиваль");
        }
        return FestivalMapper.toSummaryDto(festival);
    }

    Stage getEntityById(Long id) {
        return stageRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Stage", id));
    }

    private Festival resolveFestival(Long festivalId) {
        return festivalRepository.findById(festivalId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Festival", festivalId));
    }
}
