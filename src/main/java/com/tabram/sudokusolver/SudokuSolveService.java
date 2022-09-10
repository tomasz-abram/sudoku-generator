package com.tabram.sudokusolver;

public class SudokuSolveService {

    private static final int BOARD_SIZE = 9;


    //check row
    private static boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    //check colum
    private static boolean isNumberInColum(int[][] board, int number, int column) {
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[j][column] == number) {
                return true;
            }
        }
        return false;
    }

    //check grid box 3x3
    private static boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColum = column - column % 3;
        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColum; j < localBoxColum + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    // this method check all three row/column/box methods
    private static boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) && !isNumberInColum(board, number, column) && !isNumberInBox(board, number, row, column);
    }

    private static boolean solveBoard(int[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= BOARD_SIZE; numberToTry++) {
                        if (isValidPlacement(board, numberToTry, row, column)) {
                            board[row][column] = numberToTry;
                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
