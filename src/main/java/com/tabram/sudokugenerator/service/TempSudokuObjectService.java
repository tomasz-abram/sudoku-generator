package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.model.SudokuObject;
import com.tabram.sudokugenerator.repository.TempSudokuObjectRepository;
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
