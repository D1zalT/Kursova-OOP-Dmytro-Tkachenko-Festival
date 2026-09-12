package com.example.festival.service;

import com.example.festival.dto.PerformanceRequestDto;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.entity.Artist;
import com.example.festival.entity.Performance;
import com.example.festival.entity.Stage;
import com.example.festival.exception.ResourceNotFoundException;
import com.example.festival.mapper.PerformanceMapper;
import com.example.festival.repository.ArtistRepository;
import com.example.festival.repository.PerformanceRepository;
import com.example.festival.repository.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ArtistRepository artistRepository;
    private final StageRepository stageRepository;

    public PerformanceService(PerformanceRepository performanceRepository, ArtistRepository artistRepository,
                               StageRepository stageRepository) {
        this.performanceRepository = performanceRepository;
        this.artistRepository = artistRepository;
        this.stageRepository = stageRepository;
    }

    public List<PerformanceResponseDto> getAll() {
        return performanceRepository.findAll().stream()
                .map(PerformanceMapper::toResponseDto).collect(Collectors.toList());
    }

    public PerformanceResponseDto getById(Long id) {
        return PerformanceMapper.toResponseDto(getEntityById(id));
    }

    public PerformanceResponseDto create(PerformanceRequestDto dto) {
        Performance performance = PerformanceMapper.toEntity(dto);
        performance.setArtist(resolveArtist(dto.getArtistId()));
        performance.setStage(resolveStage(dto.getStageId()));
        return PerformanceMapper.toResponseDto(performanceRepository.save(performance));
    }

    public PerformanceResponseDto update(Long id, PerformanceRequestDto dto) {
        Performance existing = getEntityById(id);
        PerformanceMapper.updateEntity(existing, dto);
        if (dto.getArtistId() != null) {
            existing.setArtist(resolveArtist(dto.getArtistId()));
        }
        if (dto.getStageId() != null) {
            existing.setStage(resolveStage(dto.getStageId()));
        }
        return PerformanceMapper.toResponseDto(performanceRepository.save(existing));
    }

    public void delete(Long id) {
        Performance existing = getEntityById(id);
        performanceRepository.delete(existing);
    }

    // GET /analytics/performances/by-stage
    public Map<String, Long> countByStage() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : performanceRepository.countPerformancesByStage()) {
            String stageName = (String) row[1];
            Long count = (Long) row[2];
            result.put(stageName, count);
        }
        return result;
    }

    Performance getEntityById(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Performance", id));
    }

    private Artist resolveArtist(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Artist", artistId));
    }

    private Stage resolveStage(Long stageId) {
        return stageRepository.findById(stageId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Stage", stageId));
    }
}
