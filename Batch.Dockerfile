FROM azul/zulu-openjdk:21-latest
WORKDIR /app
ARG JAR_FILE=gsmgogo-api/build/libs/gsmgogo-api-0.0.1.jar
COPY ${JAR_FILE} gsmgogo-api.jar
ENV TZ=Asia/Seoul
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "gsmgogo-api.jar"]