package com.tabram.sudokusolver.repository;

import com.tabram.sudokusolver.model.SudokuBoard;
import org.springframework.stereotype.Repository;


@Repository
public class SudokuBoardRepository{

    SudokuBoard sudokuBoard;

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }

    public void setSudokuBoard(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }



}
