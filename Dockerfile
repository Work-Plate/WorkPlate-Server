# Build
FROM eclipse-temurin:17-jdk AS build
LABEL authors="pjh5365"

WORKDIR /src
COPY . /src
RUN ./gradlew build

# Run
FROM eclipse-temurin:17-jre
EXPOSE 8080
COPY  --from=build /src/build/libs/*SNAPSHOT.jar work-plate.jar

ENTRYPOINT ["java", "-jar", "work-plate.jar"]
