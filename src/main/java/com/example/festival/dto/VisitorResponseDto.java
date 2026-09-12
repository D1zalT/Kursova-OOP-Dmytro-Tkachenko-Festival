package com.example.festival.dto;

import java.util.HashSet;
import java.util.Set;

public class VisitorResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<FestivalSummaryDto> festivals = new HashSet<>();

    public VisitorResponseDto() {
    }

    public VisitorResponseDto(Long id, String firstName, String lastName, String email,
                               Set<FestivalSummaryDto> festivals) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.festivals = festivals;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<FestivalSummaryDto> getFestivals() {
        return festivals;
    }

    public void setFestivals(Set<FestivalSummaryDto> festivals) {
        this.festivals = festivals;
    }
}
