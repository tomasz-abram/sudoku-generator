package com.tabram.sudokugenerator.dto;


import com.tabram.sudokugenerator.model.SudokuObjectAbstract;
import com.tabram.sudokugenerator.validation.NumberInTheRange;
import com.tabram.sudokugenerator.validation.NumberIsValidPlacement;

@NumberInTheRange
@NumberIsValidPlacement
public class SudokuObjectDto extends SudokuObjectAbstract {
    public SudokuObjectDto() {
    }
}
