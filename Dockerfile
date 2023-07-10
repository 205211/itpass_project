FROM eclipse-temurin:17-jdk As build
COPY ./ /workspace/quiz
RUN cd /workspace/quiz && ./gradlew build

FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/quiz-0.0.1-SNAPSHOT.jar
COPY --from=build build/libs/quiz-0.0.1-SNAPSHOT.jar quiz.jar
ENTRYPOINT ["java","-jar","quiz.jar"]