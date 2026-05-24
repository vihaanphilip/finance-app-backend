# JPA & JDBC Notes

## Can JPA and Spring Data JDBC coexist?

Yes. Add `spring-boot-starter-data-jpa` alongside the existing `spring-boot-starter-data-jdbc` and Spring Boot will autoconfigure both. Keep existing `ListCrudRepository` JDBC repositories untouched and use JPA only for new entities.

**Caveats if you add JPA:**

- Set `spring.jpa.hibernate.ddl-auto=none` to prevent Hibernate from fighting the existing `schema.sql` init.
- JPA requires mutable `@Entity` classes with no-arg constructors — incompatible with the Java `record` types used throughout this project. You'd need separate entity classes.
- JPA needs explicit `@Column` mapping for snake_case fields; JDBC handles this natively via matching field names to column names.

**Recommendation:** Stay with Spring Data JDBC unless you need a JPA-specific feature (lazy loading, Hibernate-level caching, etc.). If you do add JPA, scope it to new entities only.

---

## What does `spring.sql.init.mode=always` do?

Tells Spring Boot to run `schema.sql` (and `data.sql` if present) on every startup, regardless of datasource type.

| Value | Behavior |
|---|---|
| `always` | Runs scripts on every startup |
| `embedded` (default) | Only runs for in-memory DBs (H2, HSQL, Derby) |
| `never` | Never runs scripts |

This project sets `always` because PostgreSQL is not an embedded database — the default `embedded` mode would skip `schema.sql` entirely.

Safe here because every statement in `schema.sql` uses `CREATE TABLE IF NOT EXISTS`, making re-runs idempotent.

---

## Can Spring Data JDBC auto-create tables without `schema.sql`?

No. This is a fundamental design difference from JPA:

- **JPA/Hibernate** can introspect `@Entity` classes and generate DDL via `spring.jpa.hibernate.ddl-auto`.
- **Spring Data JDBC** has no equivalent. The schema is treated as external — you own it, the framework just maps to it.

**Alternatives to hand-writing `schema.sql`:**

- **Flyway** — versioned SQL migration files (`V1__init.sql`, `V2__add_column.sql`). Tracks which migrations have run and only applies new ones. Best choice for multi-environment deployments.
- **Liquibase** — similar to Flyway but uses XML/YAML/JSON changelogs instead of raw SQL.
- **Keep `schema.sql`** — fine at small scale. The `CREATE TABLE IF NOT EXISTS` pattern keeps it safe.
