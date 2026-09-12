package com.example.festival.dto;

import java.time.LocalDate;

/**
 * Скорочене представлення Festival - використовується там, де повний
 * FestivalResponseDto (зі списком артистів) був би зайвим/надлишковим,
 * наприклад у VisitorResponseDto.festivals.
 */
public class FestivalSummaryDto {

    private Long id;
    private String name;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;

    public FestivalSummaryDto() {
    }

    public FestivalSummaryDto(Long id, String name, String city, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
