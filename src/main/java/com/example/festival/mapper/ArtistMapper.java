package com.example.festival.mapper;

import com.example.festival.dto.ArtistRequestDto;
import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.entity.Artist;

public final class ArtistMapper {

    private ArtistMapper() {
    }

    public static Artist toEntity(ArtistRequestDto dto) {
        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setGenre(dto.getGenre());
        artist.setCountry(dto.getCountry());
        return artist;
    }

    public static void updateEntity(Artist artist, ArtistRequestDto dto) {
        artist.setName(dto.getName());
        artist.setGenre(dto.getGenre());
        artist.setCountry(dto.getCountry());
    }

    public static ArtistResponseDto toResponseDto(Artist artist) {
        return new ArtistResponseDto(artist.getId(), artist.getName(), artist.getGenre(), artist.getCountry());
    }
}
