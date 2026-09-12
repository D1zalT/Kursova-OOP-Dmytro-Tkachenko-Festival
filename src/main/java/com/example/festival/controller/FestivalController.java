package com.example.festival.controller;

import com.example.festival.dto.ArtistResponseDto;
import com.example.festival.dto.ErrorResponse;
import com.example.festival.dto.FestivalRequestDto;
import com.example.festival.dto.FestivalResponseDto;
import com.example.festival.service.FestivalService;
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
import java.util.Set;

@Tag(
        name = "Управління фестивалями",
        description = "Операції створення, отримання, оновлення та видалення музичних фестивалів, " +
                "а також перегляду списку виконавців, прив'язаних до конкретного фестивалю."
)
@RestController
@RequestMapping("/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @Operation(
            summary = "Створити новий фестиваль",
            description = "Реєструє новий фестиваль у системі. Поля `name` та `city` обов'язкові " +
                    "та не можуть бути порожніми, `startDate` і `endDate` обов'язкові. " +
                    "Додатково перевіряється бізнес-правило: дата закінчення фестивалю не може " +
                    "бути раніше дати початку (при порушенні повертається 400 Bad Request). " +
                    "У полі `artistIds` можна опційно передати id виконавців, яких потрібно " +
                    "одразу прив'язати до фестивалю."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Фестиваль успішно створено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FestivalResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Помилка валідації вхідних даних або порушення бізнес-правила " +
                            "(напр., дата закінчення раніше дати початку)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<FestivalResponseDto> createFestival(@Valid @RequestBody FestivalRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(festivalService.create(dto));
    }

    @Operation(
            summary = "Отримати список фестивалів",
            description = "Повертає повний перелік фестивалів, зареєстрованих у системі, " +
                    "разом із виконавцями, прив'язаними до кожного з них."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список фестивалів успішно отримано",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = FestivalResponseDto.class))))
    })
    @GetMapping
    public ResponseEntity<List<FestivalResponseDto>> getAllFestivals() {
        return ResponseEntity.ok(festivalService.getAll());
    }

    @Operation(
            summary = "Отримати інформацію про фестиваль",
            description = "Повертає детальну інформацію про фестиваль за його ідентифікатором, " +
                    "включно зі списком прив'язаних виконавців."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Фестиваль знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FestivalResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Фестиваль з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FestivalResponseDto> getFestivalById(
            @Parameter(description = "Ідентифікатор фестивалю", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(festivalService.getById(id));
    }

    @Operation(
            summary = "Оновити дані фестивалю",
            description = "Повністю замінює дані фестивалю з вказаним id новими значеннями. " +
                    "Діють ті самі правила валідації, що й при створенні: обов'язкові `name`, " +
                    "`city`, `startDate`, `endDate`, а також перевірка, що дата закінчення " +
                    "не передує даті початку."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Дані фестивалю успішно оновлено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FestivalResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Помилка валідації вхідних даних або порушення бізнес-правила",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Фестиваль з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<FestivalResponseDto> updateFestival(
            @Parameter(description = "Ідентифікатор фестивалю, який потрібно оновити", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody FestivalRequestDto dto) {
        return ResponseEntity.ok(festivalService.update(id, dto));
    }

    @Operation(
            summary = "Видалити фестиваль",
            description = "Видаляє фестиваль з вказаним id разом з усіма пов'язаними записами " +
                    "про виступи на ньому. Операція незворотна."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Фестиваль успішно видалено", content = @Content),
            @ApiResponse(responseCode = "404", description = "Фестиваль з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFestival(
            @Parameter(description = "Ідентифікатор фестивалю, який потрібно видалити", example = "1")
            @PathVariable Long id) {
        festivalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Отримати виконавців фестивалю",
            description = "Повертає множину виконавців, прив'язаних до фестивалю з вказаним id."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список виконавців успішно отримано",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ArtistResponseDto.class)))),
            @ApiResponse(responseCode = "404", description = "Фестиваль з вказаним id не знайдено",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/artists")
    public ResponseEntity<Set<ArtistResponseDto>> getFestivalArtists(
            @Parameter(description = "Ідентифікатор фестивалю", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(festivalService.getArtistsOfFestival(id));
    }
}
