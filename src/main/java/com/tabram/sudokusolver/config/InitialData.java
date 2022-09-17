package com.tabram.sudokusolver.config;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialData {


    @Bean
    CommandLineRunner sudokuCommandLineRunner(SudokuBoardRepository sudokuBoardRepository) {
        return args -> {
            Integer[][] board = {
                    {null, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuBoardObject sudokuBoardObject = new SudokuBoardObject(board, 9, 3, 3);
            sudokuBoardRepository.setSudokuBoardObject(sudokuBoardObject);
        };
    }
}

