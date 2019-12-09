FROM openjdk:8-jdk-alpine

MAINTAINER wujbow@cn.ibm.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /cloud-test

WORKDIR /cloud-test

EXPOSE 8001

ADD ./cloud-service/cloud-test/target/cloud-test-1.0.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar cloud-test-1.0.jar
