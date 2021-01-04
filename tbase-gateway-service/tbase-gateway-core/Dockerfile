#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ADD src/main/resources/agent/skywalking-agent/
EXPOSE 10070
EXPOSE 8730
ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-javaagent:/skywalking-agent/skywalking-agent.jar","-jar","/app.jar"]