FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ADD /src/main/resources/agent /skywalking-agent/
EXPOSE 10068
EXPOSE 8719
ENTRYPOINT ["java","-javaagent:/skywalking-agent/skywalking-agent.jar","-jar","/app.jar"]
