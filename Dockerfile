FROM openjdk:8u212-jre-alpine3.9
# curl and jq are additional to healthcheck
RUN apk add curl jq
# Workspace in docker container
WORKDIR /usr/share/maven_docker
# Adding .jars, test data (orange.jpg) and dependencies from host into this image
ADD target/MavenDocker.jar MavenDocker.jar
ADD target/MavenDocker-tests.jar MavenDocker-tests.jar
ADD target/libs libs
# Adding suite files
ADD src/test/resources/second_suite.xml second_suite.xml
ADD src/test/resources/first_suite.xml first_suite.xml
ADD src/test/resources/general_suite.xml general_suite.xml
ADD src/test/resources/images/orange.jpg src/test/resources/images/orange.jpg

## Add health check script (only if run from simple docker-compose up command)
# Use "ADD healthcheck.sh healthcheck.sh" for running and updating docker image in local
ADD healthcheck.sh healthcheck.sh

# Use "RUN wget https://drive.google.com/uc?export=download&id=1KQ3CqjdUXzmzzqfSzqYsrumAQeDUwu6D&confirm=t"
# for running test suites
# RUN wget https://drive.google.com/uc?export=download&id=1KQ3CqjdUXzmzzqfSzqYsrumAQeDUwu6D&confirm=t

## To run without healthcheck using ip or container
# Always use ":" because all containers will be running in alpine
# ENTRYPOINT java -cp MavenDocker.jar:MavenDocker-tests.jar:libs/* -Dbrowser=$browser -DHUB_HOST=$HUB_HOST org.testng.TestNG $SUITE

## To run with healthcheck (only if run from simple docker-compose up command)
ENTRYPOINT sh healthcheck.sh