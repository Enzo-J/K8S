#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
#添加agent文件至docker容器中
ADD /src/main/resources/agent/ /skywalking-agent/
EXPOSE 10010
EXPOSE 8719
#添加agent后的服务启动方式
ENTRYPOINT ["java","-javaagent:/skywalking-agent/skywalking-agent.jar","-jar","/app.jar"]