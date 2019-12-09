FROM openjdk:8-jdk-alpine

MAINTAINER wujbow@cn.ibm.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /cloud-demo

WORKDIR /cloud-demo

EXPOSE 8001

ADD ./cloud-service/cloud-demo/target/cloud-demo-1.0.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar cloud-demo-1.0.jar
