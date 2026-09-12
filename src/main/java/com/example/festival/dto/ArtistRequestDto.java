package com.example.festival.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Дані для створення або оновлення виконавця")
public class ArtistRequestDto {

    @Schema(description = "Ім'я виконавця або назва гурту", example = "Okean Elzy",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Ім'я виконавця обов'язкове")
    @Size(max = 150, message = "Ім'я виконавця не може перевищувати 150 символів")
    private String name;

    @Schema(description = "Музичний жанр виконавця", example = "Rock",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Жанр обов'язковий")
    @Size(max = 100, message = "Жанр не може перевищувати 100 символів")
    private String genre;

    @Schema(description = "Країна походження виконавця", example = "Ukraine",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Країна обов'язкова")
    @Size(max = 100, message = "Назва країни не може перевищувати 100 символів")
    private String country;

    public ArtistRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
