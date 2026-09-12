package com.example.festival.controller;

import com.example.festival.dto.FestivalResponseDto;
import com.example.festival.service.ArtistService;
import com.example.festival.service.FestivalService;
import com.example.festival.service.PerformanceService;
import com.example.festival.service.VisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final FestivalService festivalService;
    private final ArtistService artistService;
    private final VisitorService visitorService;
    private final PerformanceService performanceService;

    public AnalyticsController(FestivalService festivalService, ArtistService artistService,
                                VisitorService visitorService, PerformanceService performanceService) {
        this.festivalService = festivalService;
        this.artistService = artistService;
        this.visitorService = visitorService;
        this.performanceService = performanceService;
    }

    // GET /analytics/festivals/count - Загальна кількість фестивалів
    @GetMapping("/festivals/count")
    public ResponseEntity<Long> getFestivalsCount() {
        return ResponseEntity.ok(festivalService.count());
    }

    // GET /analytics/artists/count - Кількість виконавців
    @GetMapping("/artists/count")
    public ResponseEntity<Long> getArtistsCount() {
        return ResponseEntity.ok(artistService.count());
    }

    // GET /analytics/visitors/count - Кількість відвідувачів
    @GetMapping("/visitors/count")
    public ResponseEntity<Long> getVisitorsCount() {
        return ResponseEntity.ok(visitorService.count());
    }

    // GET /analytics/performances/by-stage - Кількість виступів за сценами
    @GetMapping("/performances/by-stage")
    public ResponseEntity<Map<String, Long>> getPerformancesByStage() {
        return ResponseEntity.ok(performanceService.countByStage());
    }

    // GET /analytics/festivals/popular - Найпопулярніші фестивалі (за кількістю відвідувачів)
    @GetMapping("/festivals/popular")
    public ResponseEntity<List<FestivalResponseDto>> getPopularFestivals() {
        return ResponseEntity.ok(festivalService.getPopularFestivals());
    }
}
