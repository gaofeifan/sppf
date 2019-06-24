FROM hub.c.163.com/library/java:8-alpine

MAINTAINER alec alec@linkmoretech.com

ADD target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app.jar"]