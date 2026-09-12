package com.example.festival.dto;

public class StageResponseDto {

    private Long id;
    private String name;
    private Integer capacity;
    private FestivalSummaryDto festival;

    public StageResponseDto() {
    }

    public StageResponseDto(Long id, String name, Integer capacity, FestivalSummaryDto festival) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.festival = festival;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public FestivalSummaryDto getFestival() {
        return festival;
    }

    public void setFestival(FestivalSummaryDto festival) {
        this.festival = festival;
    }
}
