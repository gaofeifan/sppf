FROM java:8
VOLUME /tmp
ADD *.jar *.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/*.jar"]
