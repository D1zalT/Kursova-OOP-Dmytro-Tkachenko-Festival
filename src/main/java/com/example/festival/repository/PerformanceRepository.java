package com.example.festival.repository;

import com.example.festival.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    // Виступи конкретного виконавця
    List<Performance> findByArtistId(Long artistId);

    // Виступи на конкретній сцені
    List<Performance> findByStageId(Long stageId);

    // Кількість виступів, згрупована за сценами: [ [stageId, stageName, count], ... ]
    @Query("SELECT p.stage.id, p.stage.name, COUNT(p) FROM Performance p GROUP BY p.stage.id, p.stage.name")
    List<Object[]> countPerformancesByStage();
}
