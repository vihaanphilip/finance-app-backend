# Database Configuration Guide

This project supports two database environments: **Local Docker** and **Supabase** (production).

## Quick Switch

### Using Local Docker (Default)
```bash
# Default configuration - Docker will be used automatically
mvn spring-boot:run

# Or explicitly set the profile
SPRING_PROFILES_ACTIVE=docker mvn spring-boot:run
```

### Using Supabase
```bash
SPRING_PROFILES_ACTIVE=supabase mvn spring-boot:run
```

## Configuration Details

### Local Docker Configuration (`application-docker.properties`)
- **URL**: `jdbc:postgresql://localhost:5433/finance_db`
- **Username**: `vihaan`
- **Password**: `password123`
- **Start Docker**: Run `docker-compose -f compose.yaml up -d` in the backend folder

### Supabase Configuration (`application-supabase.properties`)
You need to provide your Supabase credentials via environment variables:

```bash
export SUPABASE_HOST="your-project.supabase.co"
export SUPABASE_PORT="5432"
export SUPABASE_DATABASE="postgres"
export SUPABASE_USER="postgres"
export SUPABASE_PASSWORD="your-password"
export SPRING_PROFILES_ACTIVE="supabase"

mvn spring-boot:run
```

**Get your Supabase credentials from:**
1. Go to https://app.supabase.com/project/[your-project-id]/settings/database
2. Look for "Connection string" section
3. Extract host, port, database, user, and password

### Environment Variable Setup

For convenience, create a `.env` file in your backend directory:
```bash
# .env (add to .gitignore)
SUPABASE_HOST=your-project.supabase.co
SUPABASE_PORT=5432
SUPABASE_DATABASE=postgres
SUPABASE_USER=postgres
SUPABASE_PASSWORD=your-password
SPRING_PROFILES_ACTIVE=supabase
```

Then load it when running:
```bash
source .env
mvn spring-boot:run
```

## Key Differences

| Feature | Docker | Supabase |
|---------|--------|----------|
| SSL Mode | Not required | Required (`sslmode=require`) |
| DDL Auto | `update` (flexible) | `validate` (safe for prod) |
| Connection Pool | Default | Limited (max 5 connections) |
| Location | Local | Cloud |
| Cost | Free | Free tier available |

## Troubleshooting

### Docker not connecting?
```bash
# Ensure Docker container is running
docker-compose -f compose.yaml up -d

# Check container status
docker ps | grep postgres
```

### Supabase connection timeout?
- Verify credentials are correct
- Check that your Supabase project network access allows your IP
- Ensure SSL/TLS is properly configured

### Switching between environments?
- Make sure to stop one before starting the other
- Each profile uses different connection settings
- Schema migrations may need to be reviewed when switching

