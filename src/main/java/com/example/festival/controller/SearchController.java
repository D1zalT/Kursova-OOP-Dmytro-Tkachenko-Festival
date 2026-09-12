package com.example.festival.controller;

import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.dto.FestivalResponseDto;
import com.example.festival.dto.VisitorResponseDto;
import com.example.festival.service.ArtistService;
import com.example.festival.service.FestivalService;
import com.example.festival.service.VisitorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final FestivalService festivalService;
    private final ArtistService artistService;
    private final VisitorService visitorService;

    public SearchController(FestivalService festivalService, ArtistService artistService, VisitorService visitorService) {
        this.festivalService = festivalService;
        this.artistService = artistService;
        this.visitorService = visitorService;
    }

    // GET /search/festivals?query= - Пошук фестивалів (за назвою або містом)
    @GetMapping("/festivals")
    public ResponseEntity<List<FestivalResponseDto>> searchFestivals(@RequestParam String query) {
        return ResponseEntity.ok(festivalService.search(query));
    }

    // GET /search/artists?query= - Пошук виконавців (за іменем, жанром або країною)
    @GetMapping("/artists")
    public ResponseEntity<List<ArtistResponseDto>> searchArtists(@RequestParam String query) {
        return ResponseEntity.ok(artistService.search(query));
    }

    // GET /search/visitors?query= - Пошук відвідувачів (за ім'ям, прізвищем або email)
    @GetMapping("/visitors")
    public ResponseEntity<List<VisitorResponseDto>> searchVisitors(@RequestParam String query) {
        return ResponseEntity.ok(visitorService.search(query));
    }
}
