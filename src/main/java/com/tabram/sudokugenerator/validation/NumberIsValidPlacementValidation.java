package com.tabram.sudokugenerator.validation;

import com.tabram.sudokugenerator.dto.SudokuObjectDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NumberIsValidPlacementValidation implements ConstraintValidator<NumberIsValidPlacement, SudokuObjectDto> {


    @Override
    public void initialize(NumberIsValidPlacement constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SudokuObjectDto sudokuObject, ConstraintValidatorContext constraintValidatorContext) {
        int sudokuSize = sudokuObject.getSudokuSize();
        int boxesWidth = sudokuObject.getQuantityBoxesWidth();
        int boxesHeight = sudokuObject.getQuantityBoxesHeight();
        Integer[][] board = sudokuObject.getBoard();
        for (int row = 0; row < sudokuSize; row++) {
            for (int column = 0; column < sudokuSize; column++) {
                if (sudokuObject.getBoard()[row][column] != null && !Objects.equals(sudokuObject.getBoard()[row][column], 0)) {
                    if (!isValidPlacement(board, sudokuObject.getBoard()[row][column], row, column, sudokuSize, boxesWidth, boxesHeight)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // this method check all three row/column/box methods
    public boolean isValidPlacement(Integer[][] board, Integer number, int row, int column, int sudokuSize, int boxesWidth, int boxesHeight) {
        return !isNumberInRow(board, number, row, column, sudokuSize) && !isNumberInColum(board, number, row, column, sudokuSize) && !isNumberInBox(board, number, row, column, boxesWidth, boxesHeight);
    }

    //check row
    private boolean isNumberInRow(Integer[][] board, Integer number, int row, int column, int sudokuSize) {
        for (int i = 0; i < sudokuSize; i++) {
            if (board[row][i] != null && !Objects.equals(board[row][i], 0) && i != column) {
                if (board[row][i].equals(number)) {
                    return true;
                }
            }
        }
        return false;
    }

    //check colum
    private boolean isNumberInColum(Integer[][] board, Integer number, int row, int column, int sudokuSize) {
        for (int j = 0; j < sudokuSize; j++) {
            if (!Objects.equals(board[j][column], null) && !Objects.equals(board[j][column], 0) && j != row) {
                if (board[j][column].equals(number)) {
                    return true;
                }
            }
        }
        return false;
    }

    //check grid box
    private boolean isNumberInBox(Integer[][] board, Integer number, int row, int column, int boxesWidth, int boxesHeight) {
        int homeBoxRow = row - row % boxesWidth;
        int homeBoxColum = column - column % boxesHeight;
        for (int i = homeBoxRow; i < homeBoxRow + boxesWidth; i++) {
            for (int j = homeBoxColum; j < homeBoxColum + boxesHeight; j++) {
                if (!Objects.equals(board[i][j], null) && !Objects.equals(board[i][j], 0) && (i != row && j != column)) {
                    if (board[i][j].equals(number)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
