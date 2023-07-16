FROM maven:3.9.3 AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=rappellemoi-api*.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]