# Supabase Connection Troubleshooting Log

**Date:** April 9, 2026  
**Stack:** Spring Boot 3.5.6 · Spring Data JDBC · PostgreSQL 18 · HikariCP  
**Supabase Project Ref:** `jkukgsmxwgftaxahpque`

---

## Root Cause (TL;DR)

The Supabase project's **direct database host resolves to an IPv6-only address**. The development machine's network has no IPv6 routing, so all direct connections fail. The Supavisor shared pooler (IPv4-capable) returns `FATAL: Tenant or user not found` — which per Supabase docs means incorrect credentials — and could not be resolved remotely.

**Current workaround:** Use local Docker Postgres (`SPRING_PROFILES_ACTIVE=docker`).

---

## Supabase Project DNS

```
Host:   db.jkukgsmxwgftaxahpque.supabase.co
A:      (none — no IPv4 record)
AAAA:   2406:da18:243:7419:f790:73ff:2d22:1078  (AWS ap-southeast-1 / Singapore)
```

The direct host is **IPv6-only**. Any connection attempt from a machine without IPv6 routing will fail with `NoRouteToHostException` or `could not translate host name`.

---

## Connections Attempted

### 1. Direct Connection — IPv6 (FAILED)
| Field | Value |
|---|---|
| Host | `db.jkukgsmxwgftaxahpque.supabase.co` |
| Port | `5432` |
| User | `postgres` |
| SSL | `sslmode=require` |
| **Error** | `java.net.NoRouteToHostException: No route to host` |

**Why:** Host resolves to IPv6 only. Machine has no global IPv6 routing (`nc -6` confirmed `No route to host` even to the raw IPv6 address `2406:da18:243:7419:f790:73ff:2d22:1078`).

---

### 2. Direct Connection — DNS Resolution (FAILED)
| Field | Value |
|---|---|
| Host | `db.jkukgsmxwgftaxahpque.supabase.co` |
| Port | `5432` |
| **Error** | `could not translate host name: nodename nor servname provided, or not known` |

**Why:** `getaddrinfo()` (used by psql, Java, nc) cannot resolve the hostname because the system DNS resolver does not return IPv6 (AAAA) records for regular application lookups, even though `dig` can resolve it directly.

---

### 3. Supavisor Transaction Pooler — All Regions (FAILED)
| Field | Value |
|---|---|
| Host | `aws-0-[region].pooler.supabase.com` |
| Port | `6543` |
| User | `postgres.jkukgsmxwgftaxahpque` |
| SSL | `sslmode=require` |
| **Error** | `FATAL: Tenant or user not found` |

Regions tested exhaustively:

| Region | Result |
|---|---|
| `ap-southeast-1` (Singapore) | `FATAL: Tenant or user not found` |
| `ap-southeast-2` (Sydney) | `FATAL: Tenant or user not found` |
| `ap-northeast-1` (Tokyo) | `FATAL: Tenant or user not found` |
| `ap-northeast-2` (Seoul) | `FATAL: Tenant or user not found` |
| `ap-south-1` (Mumbai) | `FATAL: Tenant or user not found` |
| `us-east-1` (N. Virginia) | `FATAL: Tenant or user not found` |
| `us-west-1` (Oregon) | `FATAL: Tenant or user not found` |
| `eu-west-1` (Ireland) | `FATAL: Tenant or user not found` |
| `eu-central-1` (Frankfurt) | `FATAL: Tenant or user not found` |
| `sa-east-1` (São Paulo) | `FATAL: Tenant or user not found` |

TCP connectivity to `aws-0-ap-southeast-1.pooler.supabase.com:6543` was confirmed reachable (IPv4). The error is an authentication/tenant lookup failure inside Supavisor.

Per [Supabase docs](https://supabase.com/docs/guides/database/connecting-to-postgres#connection-pooler): *"This error occurs when your credentials are incorrect."*

---

### 4. Supavisor Session Pooler (FAILED)
| Field | Value |
|---|---|
| Host | `aws-0-ap-southeast-1.pooler.supabase.com` |
| Port | `5432` |
| User | `postgres.jkukgsmxwgftaxahpque` |
| SSL | `sslmode=require` |
| **Error** | `FATAL: Tenant or user not found` |

Same result as transaction pooler. Session pooler is the recommended mode for Spring Boot (persistent connections), but the authentication issue blocked it.

---

### 5. Old PgBouncer on Direct Host (FAILED)
| Field | Value |
|---|---|
| Host | `db.jkukgsmxwgftaxahpque.supabase.co` |
| Port | `6543` |
| **Error** | `nc: getaddrinfo: nodename nor servname provided, or not known` |

**Why:** Same IPv6-only DNS issue as the direct connection.

---

## What Was Confirmed Working

- **Supabase project is active** — REST API at `jkukgsmxwgftaxahpque.supabase.co` returns `{"error":"requested path is invalid"}` (not a paused-project 503)
- **TCP to Supavisor pooler is reachable** — `aws-0-ap-southeast-1.pooler.supabase.com:6543` TCP connects successfully
- **Local Docker Postgres** — confirmed working on `localhost:5433`

---

## Spring Boot Configuration Changes Made

### `application-supabase.properties`
- Removed stale **JPA/Hibernate** config (`spring.jpa.*`) — project uses Spring Data JDBC, not JPA
- Removed nested Spring property placeholder defaults (caused silent resolution failures)
- Added `spring.sql.init.mode=always` for schema initialization
- Set `prepareThreshold=0` when using transaction pooler (transaction mode does not support prepared statements per Supabase docs)
- Final form uses session pooler format (port 5432) which is correct for persistent Spring Boot connections

### `Application.java`
- Briefly added `@EnableJdbcRepositories` — later removed (Spring Boot auto-configures this via `JdbcRepositoriesAutoConfiguration`)

### `.env`
- Tried multiple `SUPABASE_HOST` / `SUPABASE_PORT` / `SUPABASE_USER` combinations across all pooler variants
- Final working state: `SPRING_PROFILES_ACTIVE=docker`

---

## How to Fix Supabase Connection

Choose one of the following:

### Option A — IPv4 Add-on (Recommended for production)
1. Supabase Dashboard → Project → **Add-ons** → **IPv4 address** ($4/month)
2. This gives the direct host an IPv4 A record
3. Update `.env`:
   ```dotenv
   SPRING_PROFILES_ACTIVE=supabase
   SUPABASE_HOST=db.jkukgsmxwgftaxahpque.supabase.co
   SUPABASE_PORT=5432
   SUPABASE_USER=postgres
   ```

### Option B — Network with IPv6
Switch to any network that provides global IPv6 routing (most university/office networks). No config changes needed — just set `SPRING_PROFILES_ACTIVE=supabase` and use the direct host credentials.

### Option C — Resolve Supavisor Credentials
If you want to use the pooler from any network:
1. Go to Supabase Dashboard → **Connect** button (top of project page)
2. Select **Session pooler** tab
3. Copy the **exact** host, user, and password shown (pre-filled by Supabase)
4. Update `.env` with those exact values:
   ```dotenv
   SPRING_PROFILES_ACTIVE=supabase
   SUPABASE_HOST=aws-0-ap-southeast-1.pooler.supabase.com
   SUPABASE_PORT=5432
   SUPABASE_USER=postgres.jkukgsmxwgftaxahpque
   SUPABASE_PASSWORD=<password from Connect page>
   ```

---

## Current Working Setup

```dotenv
# .env
SPRING_PROFILES_ACTIVE=docker
```

```yaml
# compose.yaml — runs PostgreSQL 18 on localhost:5433
services:
  postgres:
    image: postgres:18.0
    ports: ["5433:5432"]
    environment:
      POSTGRES_DB: finance_db
      POSTGRES_USER: vihaan
      POSTGRES_PASSWORD: password123
```

Start with:
```zsh
docker compose up -d
./run.sh
```

