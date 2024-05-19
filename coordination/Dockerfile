FROM gradle:8.7-jdk21-alpine AS BUILD
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build -x test

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=BUILD /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]