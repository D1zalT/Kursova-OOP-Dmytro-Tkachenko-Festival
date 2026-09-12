package com.example.festival.controller;

import com.example.festival.dto.FestivalSummaryDto;
import com.example.festival.dto.VisitorRequestDto;
import com.example.festival.dto.VisitorResponseDto;
import com.example.festival.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    // POST /visitors - Зареєструвати нового відвідувача
    @PostMapping
    public ResponseEntity<VisitorResponseDto> createVisitor(@Valid @RequestBody VisitorRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visitorService.create(dto));
    }

    // GET /visitors - Отримати список відвідувачів
    @GetMapping
    public ResponseEntity<List<VisitorResponseDto>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAll());
    }

    // GET /visitors/{id} - Отримати інформацію про відвідувача
    @GetMapping("/{id}")
    public ResponseEntity<VisitorResponseDto> getVisitorById(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getById(id));
    }

    // PUT /visitors/{id} - Оновити дані відвідувача
    @PutMapping("/{id}")
    public ResponseEntity<VisitorResponseDto> updateVisitor(@PathVariable Long id,
                                                              @Valid @RequestBody VisitorRequestDto dto) {
        return ResponseEntity.ok(visitorService.update(id, dto));
    }

    // DELETE /visitors/{id} - Видалити відвідувача
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitor(@PathVariable Long id) {
        visitorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /visitors/{id}/festivals - Отримати фестивалі відвідувача
    @GetMapping("/{id}/festivals")
    public ResponseEntity<Set<FestivalSummaryDto>> getVisitorFestivals(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.getFestivalsOfVisitor(id));
    }
}
