package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.TempSudokuObjectRepository;
import org.springframework.stereotype.Service;

@Service
public class TempSudokuObjectService {

    private final TempSudokuObjectRepository tempSudokuObjectRepository;

    public TempSudokuObjectService(TempSudokuObjectRepository tempSudokuObjectRepository) {
        this.tempSudokuObjectRepository = tempSudokuObjectRepository;
    }

    public SudokuObject getSudokuObject(){
        return tempSudokuObjectRepository.getSudokuObject();
    }

    public void setSudokuObject(SudokuObject sudokuObject){
        tempSudokuObjectRepository.setSudokuObject(sudokuObject);
    }
}
