FROM openjdk:8u212-jre-alpine3.9
#Workspace in docker container
WORKDIR /usr/share/maven_docker
#Adding .jars, test data (orange.jpg) and dependencies from host into this image
ADD target/MavenDocker.jar MavenDocker.jar
ADD target/MavenDocker-tests.jar MavenDocker-tests.jar
ADD target/libs libs
#Adding suite files
ADD src/test/resources/second_suite.xml second_suite.xml
ADD src/test/resources/first_suite.xml first_suite.xml
ADD src/test/resources/general_suite.xml general_suite.xml
ADD src/test/resources/images/orange.jpg src/test/resources/images/orange.jpg
#Always use ":" because all containers will be running in alpine
ENTRYPOINT java -cp MavenDocker.jar:MavenDocker-tests.jar:libs/* -Dbrowser=$browser -DHUB_HOST=$HUB_HOST org.testng.TestNG $SUITE