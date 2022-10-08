package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateSudokuGameService<T extends SudokuObjectAbstract> {

    private final SudokuSolveService<T> sudokuSolveService;
    private final Random random = new Random();

    @Autowired
    public GenerateSudokuGameService(SudokuSolveService<T> sudokuSolveService) {

        this.sudokuSolveService = sudokuSolveService;
    }

    public void generateNumbersInDiagonalBoxes(T sudokuObject) {
        int boxWidth = sudokuObject.getQuantityBoxesWidth();
        int boxHeight = sudokuObject.getQuantityBoxesHeight();

        for (int boxW = 0; boxW < boxWidth; boxW++) {

            for (int row = boxW * boxWidth; row < boxWidth + boxW * boxWidth; row++) {
                for (int column = boxW * boxHeight; column < boxHeight + boxW * boxHeight; column++) {

                    while (sudokuObject.getValueFromArray(row, column) == 0) {
                        int intRandom = random.nextInt(sudokuObject.getSudokuSize());
                        if (sudokuSolveService.isValidPlacement(sudokuObject, intRandom + 1, row, column)) {
                            sudokuObject.setValueToArray(row, column, intRandom + 1);
                        } else {
                            sudokuObject.setValueToArray(row, column, 0);
                        }
                    }
                }
            }
        }
    }

    public void randomCleanBoard(T sudokuObject, int percent) {
        int sudokuSize = sudokuObject.getSudokuSize();
        double emptyCells = Math.pow(sudokuSize, 2) * percent / 100;
        int j = 0;
        while (j < emptyCells) {
            int iRandom = random.nextInt(sudokuObject.getSudokuSize());
            int jRandom = random.nextInt(sudokuObject.getSudokuSize());
            if (sudokuObject.getValueFromArray(iRandom, jRandom) != 0) {
                sudokuObject.setValueToArray(iRandom, jRandom, 0);
                j++;
            }
        }
    }

}

