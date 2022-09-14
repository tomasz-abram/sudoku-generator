package com.tabram.sudokusolver.dto;

import java.util.ArrayList;
import java.util.List;

public class SudokuBoardDto {

    List<ArrayList<Integer>> board;

    public SudokuBoardDto() {
    }

    public List<ArrayList<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<ArrayList<Integer>> board) {
        this.board = board;
    }
}
