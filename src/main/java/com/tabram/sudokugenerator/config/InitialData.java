package com.tabram.sudokugenerator.config;

import com.tabram.sudokugenerator.model.SudokuObject;
import com.tabram.sudokugenerator.repository.SudokuObjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialData {


    @Bean
    CommandLineRunner sudokuCommandLineRunner(SudokuObjectRepository sudokuObjectRepository) {
        return args -> {
            Integer[][] board = {
                    {0, 0, 4, 0, 6, 0, 0, 0, 2},
                    {3, 0, 0, 5, 0, 0, 0, 0, 7},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 8, 0, 0, 0, 0},
                    {0, 3, 0, 0, 4, 0, 7, 0, 8},
                    {5, 0, 0, 0, 7, 0, 0, 0, 6},
                    {0, 0, 0, 0, 0, 0, 1, 8, 0},
                    {2, 0, 0, 9, 0, 0, 0, 0, 3},
                    {0, 1, 0, 6, 0, 0, 0, 2, 0}
            };
            SudokuObject sudokuObject = new SudokuObject(board, 9, 3, 3);
            sudokuObjectRepository.setSudokuObject(sudokuObject);
        };
    }
}

