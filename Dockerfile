FROM openjdk:17-jdk-slim as builder
WORKDIR application
ARG JAR_FILE=build/libs/test_api.jar
COPY ${JAR_FILE} test_api.jar
RUN java -Djarmode=layertools -jar test_api.jar extract

# the second stage of our build will copy the extracted layers
FROM openjdk:17-jdk-slim
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
