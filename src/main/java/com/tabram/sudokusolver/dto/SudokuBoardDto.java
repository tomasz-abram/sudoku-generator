package com.tabram.sudokusolver.dto;

import java.util.Arrays;

public class SudokuBoardDto {
    int[][] board;

    public SudokuBoardDto() {
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "SudokuBoardDto{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
