package com.tabram.sudokusolver.dto;

public class SudokuBoardDto {
    int[][] board;

    public SudokuBoardDto() {
    }

    public SudokuBoardDto(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}
