#Dockerfile
FROM java:8
MAINTAINER David Malone <dmalone@pivotal.io>

VOLUME /tmp
ADD build/libs/personalfinancier.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]