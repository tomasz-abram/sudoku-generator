package com.tabram.sudokusolver.dto;


import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.validation.NumberInTheRange;
import com.tabram.sudokusolver.validation.NumberIsValidPlacement;

@NumberInTheRange
@NumberIsValidPlacement
public class SudokuObjectDto extends SudokuObjectAbstract {
    public SudokuObjectDto() {
    }
}
