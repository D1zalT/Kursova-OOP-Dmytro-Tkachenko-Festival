package com.example.festival.service;

import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.dto.FestivalRequestDto;
import com.example.festival.dto.FestivalResponseDto;
import com.example.festival.entity.Artist;
import com.example.festival.entity.Festival;
import com.example.festival.exception.InvalidRequestException;
import com.example.festival.exception.ResourceNotFoundException;
import com.example.festival.mapper.ArtistMapper;
import com.example.festival.mapper.FestivalMapper;
import com.example.festival.repository.ArtistRepository;
import com.example.festival.repository.FestivalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final ArtistRepository artistRepository;

    public FestivalService(FestivalRepository festivalRepository, ArtistRepository artistRepository) {
        this.festivalRepository = festivalRepository;
        this.artistRepository = artistRepository;
    }

    public List<FestivalResponseDto> getAll() {
        return festivalRepository.findAll().stream().map(FestivalMapper::toResponseDto).collect(Collectors.toList());
    }

    public FestivalResponseDto getById(Long id) {
        return FestivalMapper.toResponseDto(getEntityById(id));
    }

    public FestivalResponseDto create(FestivalRequestDto dto) {
        validateDates(dto);
        Festival festival = FestivalMapper.toEntity(dto);
        festival.setArtists(resolveArtists(dto.getArtistIds()));
        return FestivalMapper.toResponseDto(festivalRepository.save(festival));
    }

    public FestivalResponseDto update(Long id, FestivalRequestDto dto) {
        validateDates(dto);
        Festival existing = getEntityById(id);
        FestivalMapper.updateEntity(existing, dto);
        if (dto.getArtistIds() != null && !dto.getArtistIds().isEmpty()) {
            existing.setArtists(resolveArtists(dto.getArtistIds()));
        }
        return FestivalMapper.toResponseDto(festivalRepository.save(existing));
    }

    public void delete(Long id) {
        Festival existing = getEntityById(id);
        festivalRepository.delete(existing);
    }

    // GET /festivals/{id}/artists
    public Set<ArtistResponseDto> getArtistsOfFestival(Long id) {
        return getEntityById(id).getArtists().stream().map(ArtistMapper::toResponseDto).collect(Collectors.toSet());
    }

    public List<FestivalResponseDto> search(String query) {
        return festivalRepository.findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(query, query)
                .stream().map(FestivalMapper::toResponseDto).collect(Collectors.toList());
    }

    public List<FestivalResponseDto> getPopularFestivals() {
        return festivalRepository.findPopularFestivals().stream()
                .map(FestivalMapper::toResponseDto).collect(Collectors.toList());
    }

    public long count() {
        return festivalRepository.count();
    }

    Festival getEntityById(Long id) {
        return festivalRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Festival", id));
    }

    // Бізнес-правило: дата закінчення не може бути раніше дати початку
    private void validateDates(FestivalRequestDto dto) {
        if (dto.getStartDate() != null && dto.getEndDate() != null && dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new InvalidRequestException("Дата закінчення фестивалю не може бути раніше дати початку");
        }
    }

    private Set<Artist> resolveArtists(Set<Long> artistIds) {
        if (artistIds == null || artistIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Artist> artists = new HashSet<>();
        for (Long artistId : artistIds) {
            artists.add(artistRepository.findById(artistId)
                    .orElseThrow(() -> ResourceNotFoundException.forEntity("Artist", artistId)));
        }
        return artists;
    }
}
