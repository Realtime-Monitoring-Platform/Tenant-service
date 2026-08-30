FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Make protoc plugins executable (fixes the "not executable" error)
RUN mvn dependency:get -Dartifact=io.grpc:protoc-gen-grpc-java:1.75.0:exe:linux-x86_64 || true \
 && find /root/.m2 -name "protoc-gen-grpc-java*" -type f -exec chmod +x {} \; \
 && mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9004

ENTRYPOINT ["java", "-jar", "app.jar"]