package com.tabram.sudokusolver.dto;


import com.tabram.sudokusolver.model.SudokuBoardObjectAbstract;
import com.tabram.sudokusolver.validation.NumberInTheRange;
import com.tabram.sudokusolver.validation.NumberIsValidPlacement;

@NumberInTheRange
@NumberIsValidPlacement
public class SudokuBoardObjectDto extends SudokuBoardObjectAbstract {
    public SudokuBoardObjectDto() {
    }
}
