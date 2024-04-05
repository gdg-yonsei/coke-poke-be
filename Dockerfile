FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
VOLUME /tmp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
