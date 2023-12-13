FROM adoptopenjdk:11-jre-hotspot

WORKDIR /usr/src/app

COPY target/time-master-onpu-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "time-master-onpu-0.0.1-SNAPSHOT.jar"]