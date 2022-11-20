package com.tabram.sudokugenerator.repository;

import com.tabram.sudokugenerator.model.SudokuObject;
import org.springframework.stereotype.Repository;

@Repository
public class TempSudokuObjectRepository {

    SudokuObject sudokuObject;

    public SudokuObject getSudokuObject() {
        return sudokuObject;
    }

    public void setSudokuObject(SudokuObject sudokuObject) {
        this.sudokuObject = sudokuObject;
    }
}
