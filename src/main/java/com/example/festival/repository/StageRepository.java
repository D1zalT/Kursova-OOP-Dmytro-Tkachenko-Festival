package com.example.festival.repository;

import com.example.festival.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {

    // Сцени конкретного фестивалю
    List<Stage> findByFestivalId(Long festivalId);
}
