# Multi-Stage builder (https://docs.docker.com/develop/develop-images/multistage-build/)

####################    Build Stage(Gradle build를 위한 stage)     ####################
## build를 위한 base image를 gradle로 사용(builder는 final stage에서 활용됨)
FROM gradle:6.7.1-jdk11 AS builder
## 현재 프로젝트 폴더에서 빌드에 필요한 파일들을 base image안에 /workspace 폴더로 복사
COPY ./build.gradle /workspace/build.gradle
COPY ./settings.gradle /workspace/settings.gradle
COPY ./src /workspace/src/
## base image 안에 /workspace 폴더로 이동
WORKDIR /workspace
RUN pwd
RUN ls -al
## 현재 폴더(/workspace)에서 gradle 빌드 수행
RUN ["gradle", "bootJar"]
#######################################################################################

#################################   Final Stage     ###################################
FROM adoptopenjdk/openjdk11
COPY --from=builder /workspace/build/libs/*.jar app.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "app.jar"]
#######################################################################################