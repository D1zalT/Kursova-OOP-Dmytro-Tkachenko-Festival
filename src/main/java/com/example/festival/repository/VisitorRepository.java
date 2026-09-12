package com.example.festival.repository;

import com.example.festival.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    // Повнотекстовий пошук за ім'ям, прізвищем або email
    List<Visitor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email);

    // Перевірка унікальності email при створенні
    boolean existsByEmail(String email);

    // Перевірка унікальності email при оновленні (виключаючи поточного відвідувача)
    boolean existsByEmailAndIdNot(String email, Long id);
}
