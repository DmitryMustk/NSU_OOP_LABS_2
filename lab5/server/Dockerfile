FROM amazoncorretto:21 AS builder

WORKDIR /app

COPY gradlew gradlew.bat build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle
COPY src /app/src/

RUN ./gradlew build

FROM amazoncorretto:21

WORKDIR /app

COPY --from=builder /app/build/libs /app/libs
COPY --from=builder /app/build/classes/java/main /app/classes

CMD ["java", "-cp", "/app/classes", "ru.nsu.dmustakaev.Main"]
