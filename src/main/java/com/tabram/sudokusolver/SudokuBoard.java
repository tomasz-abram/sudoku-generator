package com.tabram.sudokusolver;

import org.springframework.stereotype.Component;

@Component
public class SudokuBoard {

    int[][] board = {
            {0, 0, 4, 0, 6, 0, 0, 0, 2},
            {3, 0, 0, 5, 0, 0, 0, 0, 7},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 8, 0, 0, 0, 0},
            {0, 3, 0, 0, 4, 0, 7, 0, 8},
            {5, 0, 0, 0, 7, 0, 0, 0, 6},
            {0, 0, 0, 0, 0, 0, 1, 8, 0},
            {2, 0, 0, 9, 0, 0, 0, 0, 3},
            {0, 1, 0, 6, 0, 0, 0, 2, 0}
    };

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}
