FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn verify --fail-never
COPY /src src
RUN mvn clean package -D maven.test.skip

FROM gcr.io/distroless/java17-debian11
COPY --from=build /app/target/user-service-0.1.0.jar user-service-0.1.0.jar
CMD ["/user-service-0.1.0.jar"]