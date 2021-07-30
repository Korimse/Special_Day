FROM openjdk:11-jre-slim

LABEL maintainer="f.mujeup01@naver.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=build/libs/special_day-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} special_day.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/special_day.jar"]
