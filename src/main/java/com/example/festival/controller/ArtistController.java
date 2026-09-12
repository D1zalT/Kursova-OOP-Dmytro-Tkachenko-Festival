package com.example.festival.controller;

import com.example.festival.dto.ArtistRequestDto;
import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.dto.ErrorResponse;
import com.example.festival.dto.PerformanceResponseDto;
import com.example.festival.service.ArtistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Управління виконавцями",
        description = "Операції створення, отримання, оновлення та видалення профілів виконавців " +
                "(музичних гуртів/артистів), а також перегляду списку їхніх виступів на фестивалях."
)
@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Operation(
            summary = "Створити нового виконавця",
            description = "Реєструє нового виконавця у системі. Поля `name`, `genre` та `country` " +
                    "обов'язкові та не можуть бути порожніми чи перевищувати допустиму довжину."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Виконавця успішно створено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArtistResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ArtistResponseDto> createArtist(@Valid @RequestBody ArtistRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artistService.create(dto));
    }

    @Operation(
            summary = "Отримати список виконавців",
            description = "Повертає повний перелік виконавців, зареєстрованих у системі."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список виконавців успішно отримано",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ArtistResponseDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<ArtistResponseDto>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAll());
    }

    @Operation(
            summary = "Отримати інформацію про виконавця",
            description = "Повертає детальну інформацію про виконавця за його ідентифікатором."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Виконавця знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArtistResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Виконавця з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> getArtistById(
            @Parameter(description = "Ідентифікатор виконавця", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(artistService.getById(id));
    }

    @Operation(
            summary = "Оновити дані виконавця",
            description = "Повністю замінює дані виконавця з вказаним id новими значеннями. " +
                    "Діють ті самі правила валідації, що й при створенні: обов'язкові `name`, " +
                    "`genre`, `country`."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Дані виконавця успішно оновлено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ArtistResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Виконавця з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> updateArtist(
            @Parameter(description = "Ідентифікатор виконавця, якого потрібно оновити", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ArtistRequestDto dto) {
        return ResponseEntity.ok(artistService.update(id, dto));
    }

    @Operation(
            summary = "Видалити виконавця",
            description = "Видаляє виконавця з вказаним id та розриває всі його зв'язки з " +
                    "фестивалями й запланованими виступами. Операція незворотна."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Виконавця успішно видалено", content = @Content),
            @ApiResponse(responseCode = "404", description = "Виконавця з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(
            @Parameter(description = "Ідентифікатор виконавця, якого потрібно видалити", example = "1")
            @PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Отримати виступи виконавця",
            description = "Повертає список запланованих виступів (Performance) вказаного виконавця " +
                    "з інформацією про час, тривалість та сцену, на якій відбувається виступ."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список виступів успішно отримано",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PerformanceResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Виконавця з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/performances")
    public ResponseEntity<List<PerformanceResponseDto>> getArtistPerformances(
            @Parameter(description = "Ідентифікатор виконавця", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(artistService.getPerformancesOfArtist(id));
    }
}
