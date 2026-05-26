# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Stack

- **Spring Boot 3.5.6**, **Java 25**, **Spring Data JDBC** (not JPA)
- **PostgreSQL 18** via Docker Compose — mapped to host port **5433**
- **opencsv** for CSV bulk import; **springdoc-openapi** for Swagger UI at `/swagger-ui.html`

## Commands

### Run the app

```zsh
./run.sh          # Always use this — sources .env before launching Spring Boot
./run-debug.sh    # Debug mode: builds JAR, starts with JDWP suspend on :5005
```

Running `./mvnw spring-boot:run` directly will miss env vars from `.env`.

### Build and test

```zsh
./mvnw clean package        # Compile + run all tests
./mvnw test                 # Tests only
./mvnw test -Dtest=ClassName#methodName   # Single test
docker compose up -d        # Start Postgres manually if needed
```

### Database profiles

| Profile | Datasource | Config file |
|---|---|---|
| `docker` (default) | Local Postgres in Docker on port 5433 | `application-docker.properties` |
| `supabase` | Cloud Postgres via env vars | `application-supabase.properties` |

Switch profiles via `SPRING_PROFILES_ACTIVE` in `.env`.

## Architecture

### Domain modules

Six packages under `com.vphilip.finance.app`, each self-contained:

```
account/    → bootstrap/, controller/, model/, repository/
earning/    → bootstrap/, controller/, dto/, exception/, model/, repository/, service/
expense/    → bootstrap/, controller/, dto/, model/, repository/
transfer/   → controller/, dto/, model/, repository/
summary/    → controller/, dto/, repository/
config/     → WebCorsConfig.java
```

The `earning` module is the most complete — use it as the reference when adding new domains. It is the only module with a `service/` layer (CSV processing and summary aggregation). All other modules call repositories directly from controllers.

### Models and DTOs

- All models and DTOs are **Java `record` types** (immutable).
- Field names use **snake_case** to match DB column names directly — no `@Column` mapping needed.
- `created_at` / `last_modified_at` are **set manually in controllers** with `LocalDateTime.now()` — there are no `@CreatedDate`/`@LastModifiedDate` annotations.
- DTOs are flat records that include joined labels (e.g., `account_label`, `earning_category_label`) returned by custom `@Query` repository methods.

### Repository pattern

All repositories extend `ListCrudRepository<Entity, IdType>`. Custom join queries use `@Query` with text blocks:

```java
@Query("""
    SELECT e.id, a.name as account_label, ec.label as earning_category_label, ...
    FROM earning e
    LEFT JOIN account a ON e.account_id = a.id
    ...
    """)
List<EarningDTO> findAllWithLabels();
```

`JdbcClientAccountTypeRepository_OBSOLETE.java` shows the old manual `JdbcClient` approach — do not replicate it.

### API URL conventions

- Accounts: `/api/accounts` (no version prefix)
- Earnings, expenses, transfers: `/api/v1/earnings`, `/api/v1/expenses`, `/api/v1/transfers`
- Summary: `/api/v1/summary/accounts?start_date=YYYY-MM-DD&end_date=YYYY-MM-DD`
- Type/category endpoints use mixed-version legacy paths — follow existing controller paths when extending.
- **Updates use `POST /{id}`**, not `PUT /{id}` (non-standard; `PUT` also exists on earnings for parity).

### Schema management

`src/main/resources/schema.sql` runs on every startup (`spring.sql.init.mode=always`) using `CREATE TABLE IF NOT EXISTS`. Schema history is tracked by dated comments inside the file. **Add new DDL at the bottom with a date comment; never remove existing statements.**

### Bootstrap / seed data

`CommandLineRunner` beans in each `bootstrap/` package load JSON files from `src/main/resources/data/`. Controlled by properties in profile files:

```properties
app.bootstrap-data=false
app.bootstrap-expense-data=false
app.bootstrap-earnings-data=false
```

There are no bootstrap runners for `transfer` or `summary`.

### CSV bulk import

`POST /api/v1/earnings/upload` accepts a multipart CSV. `EarningCsvDto` uses `@CsvBindByName`/`@CsvDate` for field mapping. `CsvProcessingException` is a domain `RuntimeException` caught by `@ExceptionHandler` in `EarningController`.

### CORS

Configured in `WebCorsConfig.java` via `cors.allowed-origins` in `application.properties`. Default allows `localhost:3000`, `localhost:5173`, `localhost:8080`.

## Notes / Conventions

- **Strategy and plan docs** go in the project's `.claude/plans/` folder (e.g., `.claude/plans/data-segregation-plan.md`), not in `~/.claude/plans/`.
