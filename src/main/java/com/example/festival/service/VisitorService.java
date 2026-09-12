package com.example.festival.service;

import com.example.festival.dto.FestivalSummaryDto;
import com.example.festival.dto.VisitorRequestDto;
import com.example.festival.dto.VisitorResponseDto;
import com.example.festival.entity.Festival;
import com.example.festival.entity.Visitor;
import com.example.festival.exception.DuplicateResourceException;
import com.example.festival.exception.ResourceNotFoundException;
import com.example.festival.mapper.FestivalMapper;
import com.example.festival.mapper.VisitorMapper;
import com.example.festival.repository.FestivalRepository;
import com.example.festival.repository.VisitorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final FestivalRepository festivalRepository;

    public VisitorService(VisitorRepository visitorRepository, FestivalRepository festivalRepository) {
        this.visitorRepository = visitorRepository;
        this.festivalRepository = festivalRepository;
    }

    public List<VisitorResponseDto> getAll() {
        return visitorRepository.findAll().stream().map(VisitorMapper::toResponseDto).collect(Collectors.toList());
    }

    public VisitorResponseDto getById(Long id) {
        return VisitorMapper.toResponseDto(getEntityById(id));
    }

    public VisitorResponseDto create(VisitorRequestDto dto) {
        assertEmailIsFree(dto.getEmail(), null);
        Visitor visitor = VisitorMapper.toEntity(dto);
        visitor.setFestivals(resolveFestivals(dto.getFestivalIds()));
        return VisitorMapper.toResponseDto(visitorRepository.save(visitor));
    }

    public VisitorResponseDto update(Long id, VisitorRequestDto dto) {
        assertEmailIsFree(dto.getEmail(), id);
        Visitor existing = getEntityById(id);
        VisitorMapper.updateEntity(existing, dto);
        if (dto.getFestivalIds() != null && !dto.getFestivalIds().isEmpty()) {
            existing.setFestivals(resolveFestivals(dto.getFestivalIds()));
        }
        return VisitorMapper.toResponseDto(visitorRepository.save(existing));
    }

    public void delete(Long id) {
        Visitor existing = getEntityById(id);
        visitorRepository.delete(existing);
    }

    // GET /visitors/{id}/festivals
    public Set<FestivalSummaryDto> getFestivalsOfVisitor(Long id) {
        return getEntityById(id).getFestivals().stream()
                .map(FestivalMapper::toSummaryDto).collect(Collectors.toSet());
    }

    public List<VisitorResponseDto> search(String query) {
        return visitorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        query, query, query)
                .stream().map(VisitorMapper::toResponseDto).collect(Collectors.toList());
    }

    public long count() {
        return visitorRepository.count();
    }

    Visitor getEntityById(Long id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("Visitor", id));
    }

    // Бізнес-правило: email відвідувача має бути унікальним
    private void assertEmailIsFree(String email, Long currentId) {
        boolean exists = (currentId == null)
                ? visitorRepository.existsByEmail(email)
                : visitorRepository.existsByEmailAndIdNot(email, currentId);
        if (exists) {
            throw new DuplicateResourceException("Відвідувач з email '" + email + "' вже зареєстрований");
        }
    }

    private Set<Festival> resolveFestivals(Set<Long> festivalIds) {
        if (festivalIds == null || festivalIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Festival> festivals = new HashSet<>();
        for (Long festivalId : festivalIds) {
            festivals.add(festivalRepository.findById(festivalId)
                    .orElseThrow(() -> ResourceNotFoundException.forEntity("Festival", festivalId)));
        }
        return festivals;
    }
}
