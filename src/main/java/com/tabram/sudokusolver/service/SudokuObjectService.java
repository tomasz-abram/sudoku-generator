package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SudokuObjectService {
    private final SudokuObjectRepository sudokuObjectRepository;

    public SudokuObjectService(SudokuObjectRepository sudokuObjectRepository) {
        this.sudokuObjectRepository = sudokuObjectRepository;
    }

    public SudokuObject getSudokuObject() {
        return sudokuObjectRepository.getSudokuObject();
    }

    public void setSudokuObject(SudokuObject sudokuObject) {
        sudokuObjectRepository.setSudokuObject(sudokuObject);
    }
}
