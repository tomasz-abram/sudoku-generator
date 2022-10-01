package com.tabram.sudokusolver.model;

public class SudokuBoardObject extends SudokuBoardObjectAbstract {
    public SudokuBoardObject() {
    }

    public SudokuBoardObject(Integer[][] board, int sudokuSize, int quantityBoxesHeight, int quantityBoxesWidth) {
        super(board, sudokuSize, quantityBoxesHeight, quantityBoxesWidth);
    }
}
