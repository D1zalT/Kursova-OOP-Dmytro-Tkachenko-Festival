package com.example.festival.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    // Visitor (M) --- (N) Festival. Visitor - володіюча сторона (таблиця visitor_festival).
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "visitor_festival",
            joinColumns = @JoinColumn(name = "visitor_id"),
            inverseJoinColumns = @JoinColumn(name = "festival_id")
    )
    private Set<Festival> festivals = new HashSet<>();

    public Visitor() {
    }

    public Visitor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Festival> getFestivals() {
        return festivals;
    }

    public void setFestivals(Set<Festival> festivals) {
        this.festivals = festivals;
    }
}
