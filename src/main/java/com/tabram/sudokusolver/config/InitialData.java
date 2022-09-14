package com.tabram.sudokusolver.config;

import com.tabram.sudokusolver.model.SudokuBoard;
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

            SudokuBoard sudokuBoard = new SudokuBoard(board, 9, 3, 3);
            sudokuBoardRepository.setSudokuBoard(sudokuBoard);
        };
    }
}

