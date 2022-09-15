package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoard;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClearBoardService {
    SudokuBoardRepository sudokuBoardRepository;

    @Autowired
    public ClearBoardService(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    public void clearBoard() {
        SudokuBoard sudokuBoard = sudokuBoardRepository.getSudokuBoard();
        int boardSize = sudokuBoardRepository.getSudokuBoard().getSudokuSize();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                sudokuBoard.getBoard()[row][column] = 0;
            }
        }
        sudokuBoardRepository.setSudokuBoard(sudokuBoard);
    }
}
