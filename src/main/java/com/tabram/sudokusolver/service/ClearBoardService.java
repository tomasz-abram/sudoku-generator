package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClearBoardService {
    SudokuObjectRepository sudokuObjectRepository;

    @Autowired
    public ClearBoardService(SudokuObjectRepository sudokuObjectRepository) {
        this.sudokuObjectRepository = sudokuObjectRepository;
    }

    public <T extends SudokuObjectAbstract> void clearBoard(T sudokuObject) {
        int boardSize = sudokuObject.getSudokuSize();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                sudokuObject.setValueToArray(row, column, null);
            }
        }
    }
}
