package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoardObject;
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

    //check row
    private boolean isNumberInRow(Integer[][] board, Integer number, int row) {
        int sudokuSize = sudokuBoardRepository.getSudokuBoardObject().getSudokuSize();
        for (int i = 0; i < sudokuSize; i++) {
            if (board[row][i].equals(number)) {
                return true;
            }
        }
        return false;
    }

    //check colum
    private boolean isNumberInColum(Integer[][] board, Integer number, int column) {
        int sudokuSize = sudokuBoardRepository.getSudokuBoardObject().getSudokuSize();
        for (int j = 0; j < sudokuSize; j++) {
            if (board[j][column].equals(number)) {
                return true;
            }
        }
        return false;
    }

    //check grid box
    private boolean isNumberInBox(Integer[][] board, Integer number, int row, int column) {
        int boxesWidth = sudokuBoardRepository.getSudokuBoardObject().getQuantityBoxesWidth();
        int boxesHeight = sudokuBoardRepository.getSudokuBoardObject().getQuantityBoxesHeight();
        int homeBoxRow = row - row % boxesWidth;
        int homeBoxColum = column - column % boxesHeight;
        for (int i = homeBoxRow; i < homeBoxRow + boxesWidth; i++) {
            for (int j = homeBoxColum; j < homeBoxColum + boxesHeight; j++) {
                if (board[i][j].equals(number)) {
                    return true;
                }
            }
        }
        return false;
    }

    // this method check all three row/column/box methods
    public boolean isValidPlacement(Integer[][] board, Integer number, int row, int column) {
        return !isNumberInRow(board, number, row) && !isNumberInColum(board, number, column) && !isNumberInBox(board, number, row, column);
    }

    public boolean solveBoard() {
        SudokuBoardObject board = sudokuBoardRepository.getSudokuBoardObject();
        for (int row = 0; row < board.getSudokuSize(); row++) {
            for (int column = 0; column < board.getSudokuSize(); column++) {
                if (board.getValueFromArray(row,column).equals(0) ) {
                    for (int numberToTry = 1; numberToTry <= board.getSudokuSize(); numberToTry++) {
                        if (isValidPlacement(board.getBoard(), numberToTry, row, column)) {
                            board.setValueToArray(row,column,numberToTry);
                            if (solveBoard()) {
                                return true;
                            } else {
                                board.setValueToArray(row,column,0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        sudokuBoardRepository.setSudokuBoardObject(board);
        return true;
    }
}
