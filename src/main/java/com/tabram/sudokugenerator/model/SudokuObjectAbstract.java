package com.tabram.sudokugenerator.model;

import java.util.Arrays;
import java.util.Objects;

public class SudokuObjectAbstract {
    Integer[][] board;
    int sudokuSize;
    int quantityBoxesHeight;
    int quantityBoxesWidth;

    public SudokuObjectAbstract() {
    }

    public SudokuObjectAbstract(Integer[][] board, int sudokuSize, int quantityBoxesHeight, int quantityBoxesWidth) {
        this.board = board;
        this.sudokuSize = sudokuSize;
        this.quantityBoxesHeight = quantityBoxesHeight;
        this.quantityBoxesWidth = quantityBoxesWidth;
    }

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    public Integer getValueFromArray(int i, int j) {
        return board[i][j];
    }
    public void setValueToArray(int i, int j, Integer value) {
        this.board[i][j] = value;
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

    @Override
    public String toString() {
        return "SudokuBoardObject{" +
                "board=" + Arrays.toString(board) +
                ", sudokuSize=" + sudokuSize +
                ", quantityBoxesHeight=" + quantityBoxesHeight +
                ", quantityBoxesWidth=" + quantityBoxesWidth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuObjectAbstract that = (SudokuObjectAbstract) o;
        return sudokuSize == that.sudokuSize && quantityBoxesHeight == that.quantityBoxesHeight && quantityBoxesWidth == that.quantityBoxesWidth && Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sudokuSize, quantityBoxesHeight, quantityBoxesWidth);
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }
}
