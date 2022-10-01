package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoardObjectAbstract;
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

    public <T extends SudokuBoardObjectAbstract> T clearBoard(T sudokuBoardObject) {
        int boardSize = sudokuBoardObject.getSudokuSize();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                sudokuBoardObject.setValueToArray(row, column, null);
            }
        }
        return sudokuBoardObject;
    }
}
