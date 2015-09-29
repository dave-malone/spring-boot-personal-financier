#Dockerfile
FROM java:8
MAINTAINER David Malone <dmalone@pivotal.io>

ENV MONGO_USERNAME username
ENV MONGO_PASSWORD password
ENV MONGO_DATABASE personalfinancier
ENV MONGO_HOST localhost
ENV MONGO_PORT 27017

VOLUME /tmp
ADD build/libs/personalfinancier-2.0.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]