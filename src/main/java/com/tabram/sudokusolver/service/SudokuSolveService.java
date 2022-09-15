package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoard;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SudokuSolveService {
private final SudokuBoardRepository sudokuBoardRepository;
@Autowired
    public SudokuSolveService(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    //check row
    private boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    //check colum
    private boolean isNumberInColum(int[][] board, int number, int column) {
        for (int j = 0; j < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); j++) {
            if (board[j][column] == number) {
                return true;
            }
        }
        return false;
    }

    //check grid box 3x3
    private boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % sudokuBoardRepository.getSudokuBoard().getQuantityBoxesHeight();
        int localBoxColum = column - column % sudokuBoardRepository.getSudokuBoard().getQuantityBoxesWidth();
        for (int i = localBoxRow; i < localBoxRow + sudokuBoardRepository.getSudokuBoard().getQuantityBoxesHeight(); i++) {
            for (int j = localBoxColum; j < localBoxColum + sudokuBoardRepository.getSudokuBoard().getQuantityBoxesWidth(); j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    // this method check all three row/column/box methods
    private boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) && !isNumberInColum(board, number, column) && !isNumberInBox(board, number, row, column);
    }


    public boolean solveBoard(int[][] board) {
        for (int row = 0; row <= sudokuBoardRepository.getSudokuBoard().getSudokuSize()-1; row++) {
            for (int column = 0; column <= sudokuBoardRepository.getSudokuBoard().getSudokuSize()-1; column++) {
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
        SudokuBoard tempBoard = sudokuBoardRepository.getSudokuBoard();
        tempBoard.setBoard(board);
        sudokuBoardRepository.setSudokuBoard(tempBoard);
        return true;
    }
}
