# festival-service — Лабораторна робота 3

Backend-сервіс управління системою музичних фестивалів (Варіант 16).
Побудована на основі лаб. роботи 2: додано DTO-класи, валідацію вхідних даних,
централізовану обробку помилок 4xx/5xx та власні класи винятків.

## Що нового порівняно з лаб. роботою 2

### 1. DTO-класи (`dto/`)
Контролери більше не приймають/повертають JPA-сутності напряму — тільки DTO:

- `*RequestDto` (`FestivalRequestDto`, `ArtistRequestDto`, `VisitorRequestDto`,
  `StageRequestDto`, `PerformanceRequestDto`) — тіло вхідних POST/PUT-запитів,
  з анотаціями Bean Validation (`@NotBlank`, `@NotNull`, `@Size`, `@Email`, `@Positive`).
  Зв'язки передаються через id (`artistIds`, `festivalIds`, `artistId`, `stageId`, `festivalId`).
- `*ResponseDto` (`FestivalResponseDto`, `ArtistResponseDto`, `VisitorResponseDto`,
  `StageResponseDto`, `PerformanceResponseDto`) — те, що повертається клієнту.
- `*SummaryDto` (`FestivalSummaryDto`, `StageSummaryDto`) — "полегшені" версії,
  щоб уникнути надто глибокої вкладеності (наприклад, у відповіді на
  `/visitors/{id}/festivals` фестиваль не тягне за собою повний список артистів).
- `ErrorResponse` — уніфікована структура тіла помилки.

### 2. Валідація (`jakarta.validation` / spring-boot-starter-validation)
Усі `POST`/`PUT`-ендпоінти позначені `@Valid @RequestBody`. Приклади правил:

| DTO                    | Поле              | Правило                                   |
|-------------------------|-------------------|--------------------------------------------|
| FestivalRequestDto      | name, city        | `@NotBlank`, обмеження довжини             |
| FestivalRequestDto      | startDate, endDate| `@NotNull`                                  |
| ArtistRequestDto        | name, genre, country | `@NotBlank`                              |
| VisitorRequestDto       | email             | `@NotBlank`, `@Email`                      |
| StageRequestDto         | capacity          | `@NotNull`, `@Positive`                    |
| StageRequestDto         | festivalId        | `@NotNull`                                  |
| PerformanceRequestDto   | durationMinutes   | `@NotNull`, `@Positive`                    |
| PerformanceRequestDto   | artistId, stageId | `@NotNull`                                  |

Бізнес-правила, які не покриваються анотаціями, перевіряються в сервісному шарі
і кидають власні винятки (див. нижче), наприклад: дата закінчення фестивалю
не може бути раніше дати початку; email відвідувача має бути унікальним.

### 3. Власні класи винятків (`exception/`)

| Клас                        | HTTP-статус | Коли кидається                                             |
|------------------------------|-------------|--------------------------------------------------------------|
| `ResourceNotFoundException`  | 404         | Сутність з вказаним `id` не знайдена в БД                    |
| `DuplicateResourceException` | 409         | Порушення унікальності (напр., email відвідувача вже зайнятий) |
| `InvalidRequestException`    | 400         | Порушення бізнес-правила (напр., дати фестивалю переплутані)  |

### 4. Централізована обробка помилок (`GlobalExceptionHandler`, `@RestControllerAdvice`)

Окрім власних винятків, глобально обробляються:
- `MethodArgumentNotValidException` (400) — помилки `@Valid`, повертає мапу `field -> message`;
- `HttpMessageNotReadableException` (400) — некоректний/відсутній JSON у тілі запиту;
- `MethodArgumentTypeMismatchException` (400) — напр. `GET /festivals/abc` замість числового id;
- `Exception` (500) — будь-яка інша непередбачена помилка.

Усі відповіді про помилки мають однаковий формат (`ErrorResponse`):
```json
{
  "timestamp": "2026-08-25T12:34:56",
  "status": 400,
  "error": "Validation Failed",
  "message": "Одне або декілька полів запиту не пройшли валідацію",
  "path": "/festivals",
  "validationErrors": {
    "name": "Назва фестивалю обов'язкова",
    "endDate": "Дата закінчення обов'язкова"
  }
}
```

### 5. Mapper-класи (`mapper/`)
Прості статичні утилітні класи (`FestivalMapper`, `ArtistMapper`, `VisitorMapper`,
`StageMapper`, `PerformanceMapper`), які конвертують `Entity <-> DTO`. Викликаються
із сервісного шару — контролери працюють винятково з DTO.

## Встановлення PostgreSQL

1. Встановіть PostgreSQL (https://www.postgresql.org/download/).
2. Створіть базу даних:
   ```sql
   CREATE DATABASE festival_db;
   ```
3. Перевірте / змініть параметри підключення в `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/festival_db
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   ```

## Запуск

```bash
cd festival-service
mvn spring-boot:run
```

> Примітка: збірку не було перевірено в цьому середовищі — немає доступу до Maven Central
> і до PostgreSQL. Перед здачею обовʼязково запустіть локально й переконайтесь, що
> застосунок піднімається, підключається до БД і всі ендпоінти працюють.

## Приклади для тестування в Postman

### Успішний запит
```
POST /artists
{
  "name": "Okean Elzy",
  "genre": "Rock",
  "country": "Ukraine"
}
```
→ `201 Created`, тіло — `ArtistResponseDto` зі згенерованим `id`.

### Помилка валідації (400)
```
POST /artists
{
  "name": "",
  "genre": "Rock"
}
```
→ `400 Bad Request`:
```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Одне або декілька полів запиту не пройшли валідацію",
  "validationErrors": {
    "name": "Ім'я виконавця обов'язкове",
    "country": "Країна обов'язкова"
  }
}
```

### Сутність не знайдена (404)
```
GET /artists/9999
```
→ `404 Not Found`:
```json
{ "status": 404, "error": "Not Found", "message": "Artist з id=9999 не знайдено" }
```

### Дублікат email (409)
```
POST /visitors  { "firstName": "Ivan", "lastName": "Petrenko", "email": "ivan@example.com" }
POST /visitors  { "firstName": "Petro", "lastName": "Ivanenko", "email": "ivan@example.com" }
```
→ другий запит поверне `409 Conflict`.

### Порушення бізнес-правила (400)
```
POST /festivals
{
  "name": "Test Fest",
  "city": "Kyiv",
  "startDate": "2026-08-10",
  "endDate": "2026-08-01"
}
```
→ `400 Bad Request`: "Дата закінчення фестивалю не може бути раніше дати початку".

## Що лишилось зробити вручну

1. Оновити колекцію Postman `lab3-Група-Прізвище`, додавши запити з прикладами
   помилок (400/404/409) поряд зі "щасливими" сценаріями.
2. Протестувати всі ендпоінти на реально піднятому сервері з підключеною БД.
3. Експортувати колекцію в JSON і покласти в корінь проєкту (поряд з `pom.xml`).
4. Залити готовий репозиторій лаб. роботи 3 на GitHub (посилання з GitHub Classroom).
#   K u r s o v a - O O P - D m y t r o - T k a c h e n k o - F e s t i v a l  
 