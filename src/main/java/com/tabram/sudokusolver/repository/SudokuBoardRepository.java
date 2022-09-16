package com.tabram.sudokusolver.repository;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import org.springframework.stereotype.Repository;


@Repository
public class SudokuBoardRepository{

    SudokuBoardObject sudokuBoardObject;

    public SudokuBoardObject getSudokuBoardObject() {
        return sudokuBoardObject;
    }

    public void setSudokuBoardObject(SudokuBoardObject sudokuBoardObject) {
        this.sudokuBoardObject = sudokuBoardObject;
    }

}
