package com.example.festival.repository;

import com.example.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FestivalRepository extends JpaRepository<Festival, Long> {

    // Повнотекстовий пошук за назвою або містом
    List<Festival> findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(String name, String city);

    // Найпопулярніші фестивалі - за кількістю зареєстрованих відвідувачів (спадання)
    @Query("SELECT f FROM Festival f LEFT JOIN f.visitors v GROUP BY f ORDER BY COUNT(v) DESC")
    List<Festival> findPopularFestivals();
}
