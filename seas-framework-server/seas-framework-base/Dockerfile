FROM openjdk:17
VOLUME /tmp
COPY target/seas-framework-base-1.0.0-exec.jar app.jar
RUN bash -c 'touch app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]