#FROM java:8
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 10062
EXPOSE 8719
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java", "-Denv=uat", "-Dskywalking.agent.service_name=tbase-microservice-service","-Dskywalking.collector.backend_service=172.16.0.10:11800","-Dskywalking.plugin.mysql.trace_sql_parameters=true","-javaagent:/Skyworking/apache-skywalking-apm-bin/skywalking-agent/agent/skywalking-agent.jar","-jar","/app.jar"]
