package com.tabram.sudokusolver.validation;

import com.tabram.sudokusolver.dto.SudokuObjectDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class NumberInTheRangeValidation implements ConstraintValidator<NumberInTheRange, SudokuObjectDto> {

    @Override
    public void initialize(NumberInTheRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SudokuObjectDto sudokuObject, ConstraintValidatorContext constraintValidatorContext) {
        int boardSize = sudokuObject.getSudokuSize();

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (!Objects.equals(sudokuObject.getBoard()[row][column], null)) {
                    if (sudokuObject.getBoard()[row][column] < 0 || sudokuObject.getBoard()[row][column] > boardSize) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
