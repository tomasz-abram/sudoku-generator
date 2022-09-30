package com.tabram.sudokusolver.validation;

import com.google.gson.Gson;
import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.model.SudokuBoardObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;


public class FileValidation implements ConstraintValidator<FileValid, FileBucket> {
    @Override
    public void initialize(FileValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(FileBucket fileBucket, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (fileBucket.getFile().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{file.empty}").addConstraintViolation();
            return false;
        }

        if (!"application/json".equals(fileBucket.getFile().getContentType())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{file.type}").addConstraintViolation();
            return false;
        }

        if (fileBucket.getFile().getSize() > 1024 * 1024) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{file.too.large}").addConstraintViolation();
            return false;
        }

        SudokuBoardObject sudokuBoardObject = new SudokuBoardObject();
        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(fileBucket.getFile().getInputStream());
            sudokuBoardObject = gson.fromJson(reader, SudokuBoardObject.class);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int size = sudokuBoardObject.getSudokuSize();
        Integer boxesHeight = sudokuBoardObject.getQuantityBoxesHeight();
        Integer boxesWidth = sudokuBoardObject.getQuantityBoxesWidth();
        Integer[][] board = sudokuBoardObject.getBoard();

        if (Objects.equals(size, 0) || Objects.equals(boxesHeight, 0) || Objects.equals(boxesWidth, 0) || Objects.equals(board, null)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{file.corrupted}").addConstraintViolation();
            return false;
        }

        if (boxesHeight * boxesWidth != size || size != board.length) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("{file.corrupted}").addConstraintViolation();
            return false;
        }

        return true;
    }

}



