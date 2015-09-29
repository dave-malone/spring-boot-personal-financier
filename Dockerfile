#Dockerfile
FROM java:8
MAINTAINER David Malone <dmalone@pivotal.io>

TODO - need to get the mongodb service info injected into this container via ENV vars

VOLUME /tmp
ADD build/libs/personalfinancier-2.0.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]