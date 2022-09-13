package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoard;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SudokuSolveService {

private final SudokuBoardRepository sudokuBoardRepository;
@Autowired
    public SudokuSolveService(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }
//    private static final int BOARD_SIZE = 9;

    //check row
    public boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    //check colum
    public boolean isNumberInColum(int[][] board, int number, int column) {
        for (int j = 0; j < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); j++) {
            if (board[j][column] == number) {
                return true;
            }
        }
        return false;
    }

    //check grid box 3x3
    public boolean isNumberInBox(int[][] board, int number, int row, int column) {
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
    public boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) && !isNumberInColum(board, number, column) && !isNumberInBox(board, number, row, column);
    }

    public boolean solveBoard(int[][] board) {
        for (int row = 0; row < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); row++) {
            for (int column = 0; column < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= sudokuBoardRepository.getSudokuBoard().getSudokuSize(); numberToTry++) {
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
        SudokuBoard board1 = sudokuBoardRepository.getSudokuBoard();
        board1.setBoard(board);
        sudokuBoardRepository.setSudokuBoard(board1);
        return true;
    }
}
