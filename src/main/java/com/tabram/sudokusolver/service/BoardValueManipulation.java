package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BoardValueManipulation {

    public SudokuBoardObject changeNullToZeroOnBoard(SudokuBoardObject boardObject) {
        for (int row = 0; row < boardObject.getSudokuSize(); row++) {
            for (int column = 0; column < boardObject.getSudokuSize(); column++) {
                if (Objects.equals(boardObject.getValueFromArray(row, column), null)) {
                    boardObject.setValueToArray(row, column, 0);
                }
            }
        }
        return boardObject;
    }

    public SudokuBoardObject changeZeroToNullOnBoard(SudokuBoardObject boardObject) {
        for (int row = 0; row < boardObject.getSudokuSize(); row++) {
            for (int column = 0; column < boardObject.getSudokuSize(); column++) {
                if (Objects.equals(boardObject.getValueFromArray(row, column), 0)) {
                    boardObject.setValueToArray(row, column, null);
                }
            }
        }
        return boardObject;
    }
}

