FROM openjdk:8-jdk-alpine

MAINTAINER wujbow@cn.ibm.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /cloud-gateway

WORKDIR /cloud-gateway

EXPOSE 80

ADD ./cloud-gateway/target/cloud-gateway-1.0.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar cloud-gateway-1.0.jar
