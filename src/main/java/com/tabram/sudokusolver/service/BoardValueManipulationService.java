package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BoardValueManipulationService<T extends SudokuObjectAbstract> {

    public void changeNullToZeroOnBoard(T sudokuObject) {
        for (int row = 0; row < sudokuObject.getSudokuSize(); row++) {
            for (int column = 0; column < sudokuObject.getSudokuSize(); column++) {
                if (Objects.equals(sudokuObject.getValueFromArray(row, column), null)) {
                    sudokuObject.setValueToArray(row, column, 0);
                }
            }
        }
    }

    public T changeZeroToNullOnBoard(T sudokuObject) {
        for (int row = 0; row < sudokuObject.getSudokuSize(); row++) {
            for (int column = 0; column < sudokuObject.getSudokuSize(); column++) {
                if (Objects.equals(sudokuObject.getValueFromArray(row, column), 0)) {
                    sudokuObject.setValueToArray(row, column, null);
                }
            }
        }
        return sudokuObject;
    }
}

