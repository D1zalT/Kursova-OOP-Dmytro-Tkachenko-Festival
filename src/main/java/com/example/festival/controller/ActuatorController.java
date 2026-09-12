package com.example.festival.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    // GET /actuator/health - Перевірка стану сервісу
    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("GET /actuator/health OK");
    }

    // GET /actuator/metrics - Список доступних метрик
    @GetMapping("/metrics")
    public ResponseEntity<String> getMetrics() {
        return ResponseEntity.ok("GET /actuator/metrics OK");
    }

    // GET /actuator/prometheus - Метрики для систем моніторингу
    @GetMapping("/prometheus")
    public ResponseEntity<String> getPrometheusMetrics() {
        return ResponseEntity.ok("GET /actuator/prometheus OK");
    }
}
