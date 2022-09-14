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
        for (int row = 0; row < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); row++) {
            for (int column = 0; column < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); column++) {
                sudokuBoard.getBoard()[row][column] = null;
            }
        }
        sudokuBoardRepository.setSudokuBoard(sudokuBoard);
    }
}
