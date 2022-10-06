package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import org.springframework.stereotype.Service;

@Service
public class SudokuSolveService<T extends SudokuObjectAbstract> {


    //Check row
    private boolean isNumberInRow(T sudokuObject, Integer number, int row) {
        int sudokuSize = sudokuObject.getSudokuSize();
        for (int i = 0; i < sudokuSize; i++) {
            if (sudokuObject.getValueFromArray(row, i).equals(number)) {
                return true;
            }
        }
        return false;
    }

    //Check colum
    private boolean isNumberInColum(T sudokuObject, Integer number, int column) {
        for (int j = 0; j < sudokuObject.getSudokuSize(); j++) {
            if (sudokuObject.getValueFromArray(j, column).equals(number)) {
                return true;
            }
        }
        return false;
    }

    //Check grid box
    public boolean isNumberInBox(T sudokuObject, Integer number, int row, int column) {
        int boxesWidth = sudokuObject.getQuantityBoxesWidth();
        int boxesHeight = sudokuObject.getQuantityBoxesHeight();
        int homeBoxRow = row - row % boxesWidth;
        int homeBoxColum = column - column % boxesHeight;
        for (int i = homeBoxRow; i < homeBoxRow + boxesWidth; i++) {
            for (int j = homeBoxColum; j < homeBoxColum + boxesHeight; j++) {
                if (sudokuObject.getValueFromArray(i, j).equals(number)) {
                    return true;
                }
            }
        }
        return false;
    }

    //This method check all three row/column/box methods
    public boolean isValidPlacement(T sudokuObject, Integer number, int row, int column) {
        return !isNumberInRow(sudokuObject, number, row) && !isNumberInColum(sudokuObject, number, column) && !isNumberInBox(sudokuObject, number, row, column);
    }

    public boolean solveBoard(T sudokuObject) {

        for (int row = 0; row < sudokuObject.getSudokuSize(); row++) {
            for (int column = 0; column < sudokuObject.getSudokuSize(); column++) {
                if (sudokuObject.getValueFromArray(row, column).equals(0)) {
                    for (int numberToTry = 1; numberToTry <= sudokuObject.getSudokuSize(); numberToTry++) {
                        if (isValidPlacement(sudokuObject, numberToTry, row, column)) {
                            sudokuObject.setValueToArray(row, column, numberToTry);
                            if (solveBoard(sudokuObject)) {
                                return true;
                            } else {
                                sudokuObject.setValueToArray(row, column, 0);
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
