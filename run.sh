#!/bin/zsh
# Loads .env then starts Spring Boot — use this instead of ./mvnw spring-boot:run directly

set -a
source "$(dirname "$0")/.env"
set +a

echo "Starting with profile: ${SPRING_PROFILES_ACTIVE:-docker}"
./mvnw spring-boot:run
