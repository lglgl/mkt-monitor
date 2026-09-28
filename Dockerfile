# =========================================================
# Stage 1: Build
# =========================================================

FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x gradlew

RUN ./gradlew clean bootJar --no-daemon


# =========================================================
# Stage 2: Runtime
# =========================================================

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 18080
EXPOSE 8081

ENV JAVA_OPTS="-XX:+UseContainerSupport"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]