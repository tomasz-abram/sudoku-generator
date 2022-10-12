FROM openjdk:17
LABEL org.opencontainers.image.source = https://github.com/tomasz-abram/sudoku-solver
COPY ./target/sudoku-solver-0.0.1-SNAPSHOT.jar sudoku-solver-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","sudoku-solver-0.0.1-SNAPSHOT.jar"]