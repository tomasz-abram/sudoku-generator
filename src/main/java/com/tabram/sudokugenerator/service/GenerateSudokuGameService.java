package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.model.SudokuObject;
import com.tabram.sudokugenerator.model.SudokuObjectAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateSudokuGameService {

    private final SudokuSolveService sudokuSolveService;
    private final ClearBoardService clearBoardService;
    private final SudokuObjectService sudokuObjectService;
    private final BoardValueManipulationService<SudokuObject> boardValueManipulationService;
    private final CopyObjectService copyObjectService;
    private final TempSudokuObjectService tempSudokuObjectService;
    private final Random random = new Random();

    @Autowired
    public GenerateSudokuGameService(SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, SudokuObjectService sudokuObjectService, BoardValueManipulationService<SudokuObject> boardValueManipulationService, CopyObjectService copyObjectService, TempSudokuObjectService tempSudokuObjectService) {
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.sudokuObjectService = sudokuObjectService;
        this.boardValueManipulationService = boardValueManipulationService;
        this.copyObjectService = copyObjectService;
        this.tempSudokuObjectService = tempSudokuObjectService;
    }

    public void generateGame(String level) {
        int percent;
        switch (level) {
            case "Easy":
                percent = 55;
                break;
            case "Medium":
                percent = 63;
                break;
            case "Hard":
                percent = 72;
                break;
            default:
                percent = 0;
        }
        SudokuObject sudokuObject = sudokuObjectService.getSudokuObject();
        clearBoardService.clearBoard(sudokuObject);
        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObject);
        generateNumbersInDiagonalBoxes(sudokuObject);
        sudokuSolveService.solveBoard(sudokuObject);
        tempSudokuObjectService.setSudokuObject(copyObjectService.deepCopy(sudokuObject));
        randomCleanBoard(sudokuObject, percent);
    }

    public <T extends SudokuObjectAbstract> void generateNumbersInDiagonalBoxes(T sudokuObject) {
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

    public <T extends SudokuObjectAbstract> void randomCleanBoard(T sudokuObject, int percent) {
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

