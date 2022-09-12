package com.tabram.sudokusolver.model;

public class SudokuBoard {
    int[][] board;
    int sudokuSize;
    int quantityBoxesHeight;
    int quantityBoxesWidth;

    public SudokuBoard(int[][] board) {
        this.board = board;
    }

    public SudokuBoard(int[][] board, int sudokuSize, int quantityBoxesHeight, int quantityBoxesWidth) {
        this.board = board;
        this.sudokuSize = sudokuSize;
        this.quantityBoxesHeight = quantityBoxesHeight;
        this.quantityBoxesWidth = quantityBoxesWidth;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getSudokuSize() {
        return sudokuSize;
    }

    public void setSudokuSize(int sudokuSize) {
        this.sudokuSize = sudokuSize;
    }

    public int getQuantityBoxesHeight() {
        return quantityBoxesHeight;
    }

    public void setQuantityBoxesHeight(int quantityBoxesHeight) {
        this.quantityBoxesHeight = quantityBoxesHeight;
    }

    public int getQuantityBoxesWidth() {
        return quantityBoxesWidth;
    }

    public void setQuantityBoxesWidth(int quantityBoxesWidth) {
        this.quantityBoxesWidth = quantityBoxesWidth;
    }
}
