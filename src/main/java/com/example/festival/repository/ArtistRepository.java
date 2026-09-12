package com.example.festival.repository;

import com.example.festival.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    // Повнотекстовий пошук за іменем, жанром або країною
    List<Artist> findByNameContainingIgnoreCaseOrGenreContainingIgnoreCaseOrCountryContainingIgnoreCase(
            String name, String genre, String country);
}
