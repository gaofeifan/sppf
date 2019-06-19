FROM hub.c.163.com/library/java:8-alpine

MAINTAINER alec alec@linkmoretech.com

ADD target/*.jar app.jar

EXPOSE 10088

ENTRYPOINT ["java", "-jar", "/app.jar"]