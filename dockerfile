FROM openjdk:23-ea-5-jdk-oraclelinux8

VOLUME /tmp

WORKDIR /dist

ADD ./target/* .

EXPOSE 8085

CMD "cd /dist"

ENTRYPOINT ["java", "-jar", "/dist/automotive-project-0.0.1-SNAPSHOT.jar"]
