package com.example.festival.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "festivals")
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;

    private LocalDate startDate;

    private LocalDate endDate;

    // Festival (1) --- (N) Stage. Festival - неволодіюча (inverse) сторона.
    @OneToMany(mappedBy = "festival", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Stage> stages = new java.util.ArrayList<>();

    // Festival (M) --- (N) Artist. Festival - володіюча сторона (таблиця festival_artist).
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "festival_artist",
            joinColumns = @JoinColumn(name = "festival_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>();

    // Festival (M) --- (N) Visitor. Festival - неволодіюча (inverse) сторона (володіє Visitor).
    @ManyToMany(mappedBy = "festivals", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Visitor> visitors = new HashSet<>();

    public Festival() {
    }

    public Festival(String name, String city, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }
}
