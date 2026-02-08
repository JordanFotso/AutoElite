# Stage 1: Build the application
# Assumes the build context is the root of the repository
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy maven wrapper and pom.xml to download dependencies
COPY VehiClub-API/mvnw .
COPY VehiClub-API/.mvn .mvn
COPY VehiClub-API/pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code for the API module
COPY VehiClub-API/src ./src

# Package the application
RUN ./mvnw package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17.0.2
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
