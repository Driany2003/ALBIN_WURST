FROM amazoncorretto:8-alpine-jdk

COPY target/kenpis-0.0.1-SNAPSHOT.war app.war

ENTRYPOINT ["java" , "-jar" , "/apps.jar"]