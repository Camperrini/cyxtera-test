FROM openjdk:8
EXPOSE 8080
WORKDIR ~/
COPY ./target/technical-test-0.0.1-SNAPSHOT.jar ./cyxtera-test/app.jar
CMD ["java", "-jar", "cyxtera-test/app.jar"]