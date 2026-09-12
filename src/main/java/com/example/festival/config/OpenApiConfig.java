package com.example.festival.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфігурація OpenAPI (Swagger) документації для festival-service.
 * Реєструє загальні метадані системи, які відображаються на головній
 * сторінці Swagger UI (/swagger-ui/index.html) та в specification-файлі
 * (/v3/api-docs).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI festivalServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Festival Service API")
                        .version("1.0.0")
                        .description("""
                                Backend-сервіс для управління системою музичних фестивалів (Варіант 16).

                                Система дозволяє:
                                - реєструвати фестивалі, майданчики (сцени), виконавців та відвідувачів;
                                - формувати розклад виступів (Performance), прив'язуючи виконавця
                                  до конкретної сцени та фестивалю з визначеним часом і тривалістю;
                                - відстежувати зв'язки «фестиваль - виконавці», «виконавець - виступи»
                                  та «відвідувач - фестивалі»;
                                - отримувати єдиний, уніфікований формат помилок (400/404/409/500)
                                  завдяки централізованому обробнику винятків.

                                Технологічний стек: Java 17, Spring Boot 3.3, Spring Web (REST),
                                Spring Data JPA, PostgreSQL, Bean Validation (jakarta.validation),
                                Springdoc OpenAPI (Swagger) для документування контракту API.
                                """)
                        .contact(new Contact()
                                .name("Кафедра програмної інженерії")
                                .email("software.engineering.dept@university.edu.ua")));
    }
}
