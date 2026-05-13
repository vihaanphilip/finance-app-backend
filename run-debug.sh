#!/bin/zsh
# Loads .env then starts Spring Boot in debug mode (JDWP on port 5005)

set -a
source "$(dirname "$0")/.env"
set +a

echo "Building JAR..."
./mvnw package -DskipTests -q

echo "Starting in DEBUG mode with profile: ${SPRING_PROFILES_ACTIVE:-docker}"
echo "JVM is SUSPENDED — attach your debugger to port 5005 to continue"
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=0.0.0.0:5005 \
     -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-docker} \
     -jar target/backend-0.0.1-SNAPSHOT.jar

