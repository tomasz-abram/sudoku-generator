package com.tabram.sudokusolver.repository;

import com.tabram.sudokusolver.model.SudokuObject;
import org.springframework.stereotype.Repository;


@Repository
public class SudokuObjectRepository {

    SudokuObject sudokuObject;

    public SudokuObject getSudokuObject() {
        return sudokuObject;
    }

    public void setSudokuObject(SudokuObject sudokuObject) {
        this.sudokuObject = sudokuObject;
    }

}
