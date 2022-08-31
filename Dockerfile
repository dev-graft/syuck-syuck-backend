FROM openjdk:17-jdk-slim
ENV PROFILE default
ENV TZ Asia/Seoul
RUN apt-get update && apt-get install -y fontconfig libfreetype6 && apt-get install -y tzdata
COPY applications/app-demo/build/libs/*.jar app.jar
ENTRYPOINT java -jar -Dspring.profiles.active=$PROFILE app.jar
