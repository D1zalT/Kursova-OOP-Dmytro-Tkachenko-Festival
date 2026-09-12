package com.example.festival.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

public class VisitorRequestDto {

    @NotBlank(message = "Ім'я обов'язкове")
    @Size(max = 100, message = "Ім'я не може перевищувати 100 символів")
    private String firstName;

    @NotBlank(message = "Прізвище обов'язкове")
    @Size(max = 100, message = "Прізвище не може перевищувати 100 символів")
    private String lastName;

    @NotBlank(message = "Email обов'язковий")
    @Email(message = "Email має бути коректним, наприклад user@example.com")
    private String email;

    // Список id фестивалів, на які реєструється відвідувач (необов'язково)
    private Set<Long> festivalIds = new HashSet<>();

    public VisitorRequestDto() {
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

    public Set<Long> getFestivalIds() {
        return festivalIds;
    }

    public void setFestivalIds(Set<Long> festivalIds) {
        this.festivalIds = festivalIds;
    }
}
