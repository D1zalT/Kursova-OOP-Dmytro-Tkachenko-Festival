package com.example.festival.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Дані для створення або оновлення фестивалю")
public class FestivalRequestDto {

    @Schema(description = "Назва фестивалю", example = "Atlas Weekend",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Назва фестивалю обов'язкова")
    @Size(max = 200, message = "Назва фестивалю не може перевищувати 200 символів")
    private String name;

    @Schema(description = "Місто, в якому проводиться фестиваль", example = "Київ",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Місто обов'язкове")
    @Size(max = 100, message = "Назва міста не може перевищувати 100 символів")
    private String city;

    @Schema(description = "Дата початку фестивалю", example = "2026-07-10",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата початку обов'язкова")
    private LocalDate startDate;

    @Schema(description = "Дата закінчення фестивалю (не може бути раніше startDate)",
            example = "2026-07-13", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Дата закінчення обов'язкова")
    private LocalDate endDate;

    @Schema(description = "Ідентифікатори виконавців, яких потрібно одразу прив'язати до " +
            "фестивалю (необов'язкове поле)", example = "[1, 2, 3]")
    // Список id виконавців, яких потрібно прив'язати до фестивалю (необов'язково)
    private Set<Long> artistIds = new HashSet<>();

    public FestivalRequestDto() {
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

    public Set<Long> getArtistIds() {
        return artistIds;
    }

    public void setArtistIds(Set<Long> artistIds) {
        this.artistIds = artistIds;
    }
}
