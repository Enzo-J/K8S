#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ADD /src/main/resources/agent/skywalking-agent/
EXPOSE 10071
EXPOSE 8729
ENTRYPOINT ["java","-jar","/app.jar"]
#添加agent后的服务启动方式
ENTRYPOINT ["java","-javaagent:/skywalking-agent/skywalking-agent.jar","-jar","/app.jar"]
