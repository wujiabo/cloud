FROM openjdk:8-jdk-alpine

MAINTAINER wujbow@cn.ibm.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /cloud-config

WORKDIR /cloud-config

EXPOSE 8002

ADD ./cloud-config/target/cloud-config-1.0.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar cloud-config-1.0.jar
