package com.example.festival.controller;

import com.example.festival.dto.FestivalSummaryDto;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.dto.StageRequestDto;
import com.example.festival.dto.StageResponseDto;
import com.example.festival.service.StageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stages")
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    // POST /stages - Створити нову сцену
    @PostMapping
    public ResponseEntity<StageResponseDto> createStage(@Valid @RequestBody StageRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stageService.create(dto));
    }

    // GET /stages - Отримати список сцен
    @GetMapping
    public ResponseEntity<List<StageResponseDto>> getAllStages() {
        return ResponseEntity.ok(stageService.getAll());
    }

    // GET /stages/{id} - Отримати інформацію про сцену
    @GetMapping("/{id}")
    public ResponseEntity<StageResponseDto> getStageById(@PathVariable Long id) {
        return ResponseEntity.ok(stageService.getById(id));
    }

    // PUT /stages/{id} - Оновити сцену
    @PutMapping("/{id}")
    public ResponseEntity<StageResponseDto> updateStage(@PathVariable Long id,
                                                          @Valid @RequestBody StageRequestDto dto) {
        return ResponseEntity.ok(stageService.update(id, dto));
    }

    // DELETE /stages/{id} - Видалити сцену
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        stageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /stages/{id}/performances - Отримати виступи сцени
    @GetMapping("/{id}/performances")
    public ResponseEntity<List<PerformanceResponseDto>> getStagePerformances(@PathVariable Long id) {
        return ResponseEntity.ok(stageService.getPerformancesOfStage(id));
    }

    // GET /stages/{id}/festival - Отримати фестиваль сцени
    @GetMapping("/{id}/festival")
    public ResponseEntity<FestivalSummaryDto> getStageFestival(@PathVariable Long id) {
        return ResponseEntity.ok(stageService.getFestivalOfStage(id));
    }
}
