package com.example.festival.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class StageRequestDto {

    @NotBlank(message = "Назва сцени обов'язкова")
    @Size(max = 150, message = "Назва сцени не може перевищувати 150 символів")
    private String name;

    @NotNull(message = "Місткість сцени обов'язкова")
    @Positive(message = "Місткість сцени має бути додатнім числом")
    private Integer capacity;

    @NotNull(message = "id фестивалю обов'язковий - сцена має належати фестивалю")
    private Long festivalId;

    public StageRequestDto() {
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

    public Long getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Long festivalId) {
        this.festivalId = festivalId;
    }
}
