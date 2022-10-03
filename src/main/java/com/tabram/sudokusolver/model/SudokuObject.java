package com.tabram.sudokusolver.model;

public class SudokuObject extends SudokuObjectAbstract {
    public SudokuObject() {
    }

    public SudokuObject(Integer[][] board, int sudokuSize, int quantityBoxesHeight, int quantityBoxesWidth) {
        super(board, sudokuSize, quantityBoxesHeight, quantityBoxesWidth);
    }
}
