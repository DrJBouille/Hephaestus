FROM docker.io/library/eclipse-temurin:25-jdk AS build

WORKDIR /plugin

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew shadowJar --no-daemon

FROM docker.io/itzg/minecraft-server:java25

COPY --from=build /plugin/build/libs/hephaestus.jar /plugins/hephaestus.jar