package com.example.festival.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Дані виконавця, які повертаються клієнту")
public class ArtistResponseDto {

    @Schema(description = "Унікальний ідентифікатор виконавця", example = "1")
    private Long id;

    @Schema(description = "Ім'я виконавця або назва гурту", example = "Okean Elzy")
    private String name;

    @Schema(description = "Музичний жанр виконавця", example = "Rock")
    private String genre;

    @Schema(description = "Країна походження виконавця", example = "Ukraine")
    private String country;

    public ArtistResponseDto() {
    }

    public ArtistResponseDto(Long id, String name, String genre, String country) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.country = country;
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
