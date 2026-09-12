package com.example.festival.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime performanceTime;

    private Integer durationMinutes;

    // Performance (N) --- (1) Artist. Performance - володіюча сторона (FK artist_id).
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    // Performance (N) --- (1) Stage. Performance - володіюча сторона (FK stage_id).
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    public Performance() {
    }

    public Performance(LocalDateTime performanceTime, Integer durationMinutes) {
        this.performanceTime = performanceTime;
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPerformanceTime() {
        return performanceTime;
    }

    public void setPerformanceTime(LocalDateTime performanceTime) {
        this.performanceTime = performanceTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
