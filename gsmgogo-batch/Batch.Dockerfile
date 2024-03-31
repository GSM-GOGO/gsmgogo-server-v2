FROM azul/zulu-openjdk:21-latest
WORKDIR /app
ARG JAR_FILE=build/libs/gsmgogo-batch-0.0.1.jar
COPY ${JAR_FILE} gsmgogo-batch.jar
ENV TZ=Asia/Seoul
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "gsmgogo-batch.jar"]