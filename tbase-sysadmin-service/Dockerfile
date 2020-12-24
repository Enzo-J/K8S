#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ADD ${currentProjectName}/src/main/resources/agent /skywalking-agent/
EXPOSE 10063
EXPOSE 8720
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-javaagent:/agent/skywalking-agent.jar","-jar","/app.jar"]
#ENTRYPOINT ["java", "-Denv=uat", "-Dskywalking.agent.service_name=tbase-sysadmin-service","-Dskywalking.collector.backend_service=172.16.0.10:11800","-Dskywalking.plugin.mysql.trace_sql_parameters=true","-javaagent:/usr/local/agent/skywalking-agent.jar","-jar","/app.jar"]
