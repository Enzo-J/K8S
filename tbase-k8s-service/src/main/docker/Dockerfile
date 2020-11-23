#FROM命令定义构建镜像的基础镜像，该条必须是dockerfile的首个命令
FROM java:alpine
#用于指定持久化目录

VOLUME /temp
#将本地文件添加到容器中，tar类型文件会自动解压(网络压缩资源不会被解压)，可以访问网络资源，类似wget
ADD tbase-k8s-service-1.0-SNAPSHOT.jar app.jar
#构建镜像时执行的命令
#构建镜像时执行的命令
ENTRYPOINT ["java","-jar","/app.jar"]