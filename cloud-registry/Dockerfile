FROM openjdk:8-jdk-alpine

MAINTAINER wujbow@cn.ibm.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /cloud-registry

WORKDIR /cloud-registry

EXPOSE 8001

ADD ./cloud-registry/target/cloud-registry-1.0.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar cloud-registry-1.0.jar
