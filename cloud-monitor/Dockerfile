FROM openjdk:8-jdk-alpine

MAINTAINER wujbow@cn.ibm.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /cloud-monitor

WORKDIR /cloud-monitor

EXPOSE 8003

ADD ./cloud-monitor/target/cloud-monitor-1.0.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar cloud-monitor-1.0.jar
