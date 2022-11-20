FROM openjdk:17
LABEL org.opencontainers.image.source = https://github.com/tomasz-abram/sudoku-generator
COPY ./target/sudoku-generator-0.0.1-SNAPSHOT.jar sudoku-generator-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","sudoku-generator-0.0.1-SNAPSHOT.jar"]