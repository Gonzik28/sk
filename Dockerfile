FROM maven:3.8.3-openjdk-17 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17.0.1-jdk-slim
RUN mkdir /opt/app
COPY --from=MAVEN_BUILD target/superKassa-0.0.1-SNAPSHOT.jar /opt/app

ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","/opt/app/superKassa-0.0.1-SNAPSHOT.jar"]