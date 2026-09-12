package com.example.festival.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Дані фестивалю, які повертаються клієнту")
public class FestivalResponseDto {

    @Schema(description = "Унікальний ідентифікатор фестивалю", example = "1")
    private Long id;

    @Schema(description = "Назва фестивалю", example = "Atlas Weekend")
    private String name;

    @Schema(description = "Місто, в якому проводиться фестиваль", example = "Київ")
    private String city;

    @Schema(description = "Дата початку фестивалю", example = "2026-07-10")
    private LocalDate startDate;

    @Schema(description = "Дата закінчення фестивалю", example = "2026-07-13")
    private LocalDate endDate;

    @Schema(description = "Виконавці, прив'язані до цього фестивалю")
    private Set<ArtistResponseDto> artists = new HashSet<>();

    public FestivalResponseDto() {
    }

    public FestivalResponseDto(Long id, String name, String city, LocalDate startDate, LocalDate endDate,
                                Set<ArtistResponseDto> artists) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
        this.artists = artists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<ArtistResponseDto> getArtists() {
        return artists;
    }

    public void setArtists(Set<ArtistResponseDto> artists) {
        this.artists = artists;
    }
}
