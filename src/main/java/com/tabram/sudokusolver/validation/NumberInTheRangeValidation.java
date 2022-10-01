package com.tabram.sudokusolver.validation;

import com.tabram.sudokusolver.dto.SudokuBoardObjectDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NumberInTheRangeValidation implements ConstraintValidator<NumberInTheRange, SudokuBoardObjectDto> {

    @Override
    public void initialize(NumberInTheRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SudokuBoardObjectDto sudokuBoardObject, ConstraintValidatorContext constraintValidatorContext) {
        int boardSize = sudokuBoardObject.getSudokuSize();

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (!Objects.equals(sudokuBoardObject.getBoard()[row][column], null)) {
                    if (sudokuBoardObject.getBoard()[row][column] < 0 || sudokuBoardObject.getBoard()[row][column] > boardSize) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
