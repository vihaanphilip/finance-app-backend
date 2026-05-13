# Finance Application – Agent Guide

## Stack
- **Spring Boot 3.5.6**, **Java 25**, **Spring Data JDBC** (not Spring Data JPA)
- **PostgreSQL 18** via Docker Compose on port **5433** (mapped from container's 5432)
- **opencsv** for CSV bulk import; **springdoc-openapi** for Swagger UI at `/swagger-ui.html`

## Developer Workflow

### Starting the app
```zsh
./run.sh          # ALWAYS use this – sources .env before launching Spring Boot
```
`./mvnw spring-boot:run` alone will miss env vars. The `docker` profile is the default; change via `SPRING_PROFILES_ACTIVE` in `.env`.

### Building & testing
```zsh
./mvnw clean package        # compile + run tests
./mvnw test                 # tests only
docker compose up -d        # start Postgres if Spring's auto-start isn't enough
```

### Database profiles
| Profile | Use | Config file |
|---|---|---|
| `docker` (default) | Local Postgres in Docker | `application-docker.properties` |
| `supabase` | Cloud Postgres | `application-supabase.properties` |

## Architecture

### Domain modules
Three feature packages under `com.vphilip.finance.app`, each self-contained:
```
account/    → bootstrap/, controller/, model/, repository/
earning/    → bootstrap/, controller/, dto/, exception/, model/, repository/, service/
expense/    → bootstrap/, controller/, dto/, model/, repository/
```
Only `earning` has a `service/` layer (CSV processing + summary aggregation). All other modules call repositories directly from controllers.

### Domain model conventions
- All models and DTOs are **Java `record` types** (immutable).
- Field names use **snake_case** to match DB column names directly (no mapping needed).
- Entity records carry `created_at` / `last_modified_at` fields; these are **set manually in controllers** using `LocalDateTime.now()` — there are no `@CreatedDate`/`@LastModifiedDate` annotations.
- DTOs (in `dto/`) are flat records that include joined labels (e.g., `account_label`, `earning_category_label`) returned by custom `@Query` methods.

### Repository pattern
All repositories extend `ListCrudRepository<Entity, IdType>` (Spring Data JDBC). Custom join queries use `@Query` with Java text blocks:
```java
// EarningRepository.java – canonical example
@Query("""
    SELECT e.id, a.name as account_label, ec.label as earning_category_label, ...
    FROM earning e
    LEFT JOIN account a ON e.account_id = a.id
    ...
    """)
List<EarningDTO> findAllWithLabels();
```
> `JdbcClientAccountTypeRepository_OBSOLETE.java` shows the old manual `JdbcClient` approach – **do not replicate it**.

### API URL conventions
- Accounts: `/api/accounts` (no version prefix)
- Earnings & Expenses: `/api/v1/earnings`, `/api/v1/expenses` (versioned)
- **Updates use `POST /{id}`**, not `PUT /{id}` (non-standard; `PUT` also exists on earnings but is kept for parity).

### Schema management
`src/main/resources/schema.sql` runs on every startup (`spring.sql.init.mode=always`) using `CREATE TABLE IF NOT EXISTS`. Schema history is tracked by dated comments inside the file — **add new DDL at the bottom with a date comment**, never drop existing statements.

### Bootstrap / seed data
`CommandLineRunner` beans in each `bootstrap/` package load JSON files from `src/main/resources/data/` (e.g., `accounts.json`, `earning_categories.json`). Controlled by properties:
```properties
app.bootstrap-data=false
app.bootstrap-transaction-data=false
app.bootstrap-expense-data=true   # default true in application.properties
```

### CSV bulk import (earnings only)
`POST /api/v1/earnings/upload` accepts a multipart CSV. `EarningCsvDto` uses `@CsvBindByName` / `@CsvDate` for field mapping. `CsvProcessingException` is a domain-specific `RuntimeException` caught by `@ExceptionHandler` in `EarningController`.

### CORS
Configured in `WebCorsConfig.java` via `cors.*` properties. Default allows `localhost:3000`, `localhost:5173`, `localhost:8080`.

### TestRunner
`com.vphilip.finance.app.test.TestRunner` is a `CommandLineRunner` for ad-hoc smoke tests on startup, guarded by `app.bootstrap-test-runner=true`. Use it for quick service verification during development.

## Key Files
| File | Purpose |
|---|---|
| `src/main/resources/schema.sql` | Authoritative DB schema (DDL history in comments) |
| `src/main/resources/application.properties` | Base config; profile defaults |
| `src/main/resources/data/*.json` | Seed data for bootstrap runners |
| `compose.yaml` | Local Postgres definition |
| `run.sh` | Correct entry point for local dev |
| `src/main/java/.../earning/` | Most complete module; reference for new domains |

