package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BoardValueManipulation {

    SudokuBoardRepository sudokuBoardRepository;

    @Autowired
    public BoardValueManipulation(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    public SudokuBoardObject changeNullToZeroOnBoard(SudokuBoardObject sudokuBoardObject) {
        Integer[][] sudokuBoard = sudokuBoardObject.getBoard();
        for (int row = 0; row < sudokuBoardRepository.getSudokuBoardObject().getSudokuSize(); row++) {
            for (int column = 0; column < sudokuBoardRepository.getSudokuBoardObject().getSudokuSize(); column++) {
                if (Objects.equals(sudokuBoardObject.getValueFromArray(row,column), null)) {
                    sudokuBoardObject.setValueToArray(row,column,0);
                }
            }
        }
        sudokuBoardObject.setBoard(sudokuBoard);
        return sudokuBoardObject;
    }
}

