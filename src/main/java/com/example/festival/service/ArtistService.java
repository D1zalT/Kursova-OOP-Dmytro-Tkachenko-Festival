package com.example.festival.service;

import com.example.festival.dto.ArtistRequestDto;
import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.entity.Artist;
import com.example.festival.exception.ResourceNotFoundException;
import com.example.festival.mapper.ArtistMapper;
import com.example.festival.mapper.PerformanceMapper;
import com.example.festival.repository.ArtistRepository;
import com.example.festival.repository.PerformanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final PerformanceRepository performanceRepository;

    public ArtistService(ArtistRepository artistRepository, PerformanceRepository performanceRepository) {
        this.artistRepository = artistRepository;
        this.performanceRepository = performanceRepository;
    }

    public List<ArtistResponseDto> getAll() {
        return artistRepository.findAll().stream().map(ArtistMapper::toResponseDto).collect(Collectors.toList());
    }

    public ArtistResponseDto getById(Long id) {
        return ArtistMapper.toResponseDto(getEntityById(id));
    }

    public ArtistResponseDto create(ArtistRequestDto dto) {
        Artist artist = ArtistMapper.toEntity(dto);
        return ArtistMapper.toResponseDto(artistRepository.save(artist));
    }

    public ArtistResponseDto update(Long id, ArtistRequestDto dto) {
        Artist existing = getEntityById(id);
        ArtistMapper.updateEntity(existing, dto);
        return ArtistMapper.toResponseDto(artistRepository.save(existing));
    }

    public void delete(Long id) {
        Artist existing = getEntityById(id);
        artistRepository.delete(existing);
    }

    // GET /artists/{id}/performances
    public List<PerformanceResponseDto> getPerformancesOfArtist(Long id) {
        getEntityById(id); // перевірка існування
        return performanceRepository.findByArtistId(id).stream()
                .map(PerformanceMapper::toResponseDto).collect(Collectors.toList());
    }

    public List<ArtistResponseDto> search(String query) {
        return artistRepository.findByNameContainingIgnoreCaseOrGenreContainingIgnoreCaseOrCountryContainingIgnoreCase(
                        query, query, query)
                .stream().map(ArtistMapper::toResponseDto).collect(Collectors.toList());
    }

    public long count() {
        return artistRepository.count();
    }

    Artist getEntityById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Artist", id));
    }
}
