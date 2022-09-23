package com.tabram.sudokusolver.model;

import com.tabram.sudokusolver.validation.NumberInTheRange;
import com.tabram.sudokusolver.validation.NumberIsValidPlacement;

import java.util.Arrays;

@NumberInTheRange
@NumberIsValidPlacement
public class SudokuBoardObject {
    Integer[][] board;
    int sudokuSize;
    int quantityBoxesHeight;
    int quantityBoxesWidth;

    public SudokuBoardObject() {
    }

    public SudokuBoardObject(Integer[][] board, int sudokuSize, int quantityBoxesHeight, int quantityBoxesWidth) {
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
}
