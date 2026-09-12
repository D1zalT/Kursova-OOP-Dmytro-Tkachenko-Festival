package com.example.festival.controller;

import com.example.festival.dto.PerformanceRequestDto;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.service.PerformanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    // POST /performances - Створити новий виступ
    @PostMapping
    public ResponseEntity<PerformanceResponseDto> createPerformance(@Valid @RequestBody PerformanceRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(performanceService.create(dto));
    }

    // GET /performances - Отримати список виступів
    @GetMapping
    public ResponseEntity<List<PerformanceResponseDto>> getAllPerformances() {
        return ResponseEntity.ok(performanceService.getAll());
    }

    // GET /performances/{id} - Отримати інформацію про виступ
    @GetMapping("/{id}")
    public ResponseEntity<PerformanceResponseDto> getPerformanceById(@PathVariable Long id) {
        return ResponseEntity.ok(performanceService.getById(id));
    }

    // PUT /performances/{id} - Оновити виступ
    @PutMapping("/{id}")
    public ResponseEntity<PerformanceResponseDto> updatePerformance(@PathVariable Long id,
                                                                      @Valid @RequestBody PerformanceRequestDto dto) {
        return ResponseEntity.ok(performanceService.update(id, dto));
    }

    // DELETE /performances/{id} - Видалити виступ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformance(@PathVariable Long id) {
        performanceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
