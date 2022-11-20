package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.dto.SudokuObjectDto;
import com.tabram.sudokugenerator.model.SudokuObject;
import com.tabram.sudokugenerator.model.SudokuObjectAbstract;
import com.tabram.sudokugenerator.validation.CompareWithTempSudokuBoardValidation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SudokuSolveService {

    private final SudokuObjectService sudokuObjectService;
    private final BoardValueManipulationService<SudokuObjectDto> boardValueManipulationService;
    private final MapperService mapperService;
    private final TempSudokuObjectService tempSudokuObjectService;
    private final CompareWithTempSudokuBoardValidation compareWithTempSudokuBoardValidation;
    private final MatcherService matcherService;

    public SudokuSolveService(SudokuObjectService sudokuObjectService, BoardValueManipulationService<SudokuObjectDto> boardValueManipulationService, MapperService mapperService, TempSudokuObjectService tempSudokuObjectService, CompareWithTempSudokuBoardValidation compareWithTempSudokuBoardValidation, MatcherService matcherService) {
        this.sudokuObjectService = sudokuObjectService;
        this.boardValueManipulationService = boardValueManipulationService;
        this.mapperService = mapperService;
        this.tempSudokuObjectService = tempSudokuObjectService;
        this.compareWithTempSudokuBoardValidation = compareWithTempSudokuBoardValidation;
        this.matcherService = matcherService;
    }


    public void solveAll(SudokuObjectDto sudokuObjectDto) {
        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObjectDto);
       /*
          It checks if the resolved table is in the tempSudokuObject, if so, it gets the solution from tempSudokuObject,
          otherwise it resolves the table.
         */
        if (compareWithTempSudokuBoardValidation.compare(sudokuObjectDto)) {
            SudokuObject sudokuObject = tempSudokuObjectService.getSudokuObject();
            sudokuObjectService.setSudokuObject(sudokuObject);
            tempSudokuObjectService.setSudokuObject(null);
        } else {
            solveBoard(sudokuObjectDto);
            sudokuObjectService.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        }
    }

    public void solveCell(SudokuObjectDto sudokuObjectDto, String cellId) {
  /*
          This gets information about the selected cell.
         */
        List<String> list = matcherService.cellIdMatcher(cellId);
        int i = Integer.parseInt(list.get(0));
        int j = Integer.parseInt(list.get(1));

        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObjectDto);
        /*
         * This checks if there is already a solution in TempSudokuObject, if solution exists, it gets a value from TempSudokuObject.
         * Otherwise, to get a single cell solution it has to solve the table.
         * After the table is resolved, the table is stored at TempSudokuObject.
         */
        if (compareWithTempSudokuBoardValidation.compare(sudokuObjectDto)) {
            Integer solveCell = tempSudokuObjectService.getSudokuObject().getValueFromArray(i, j);
            sudokuObjectService.getSudokuObject().setValueToArray(i, j, solveCell);
        } else {
            solveBoard(sudokuObjectDto);
            SudokuObject tempBoardObject = mapperService.mapperToSudokuBoardObject(sudokuObjectDto);
            tempSudokuObjectService.setSudokuObject(tempBoardObject);
            Integer solveCell = tempSudokuObjectService.getSudokuObject().getValueFromArray(i, j);
            sudokuObjectService.getSudokuObject().setValueToArray(i, j, solveCell);
        }
    }

    //Check row
    private <T extends SudokuObjectAbstract> boolean isNumberInRow(T sudokuObject, Integer number, int row) {
        int sudokuSize = sudokuObject.getSudokuSize();
        for (int i = 0; i < sudokuSize; i++) {
            if (sudokuObject.getValueFromArray(row, i).equals(number)) {
                return true;
            }
        }
        return false;
    }

    //Check colum
    private <T extends SudokuObjectAbstract> boolean isNumberInColum(T sudokuObject, Integer number, int column) {
        for (int j = 0; j < sudokuObject.getSudokuSize(); j++) {
            if (sudokuObject.getValueFromArray(j, column).equals(number)) {
                return true;
            }
        }
        return false;
    }

    //Check grid box
    public <T extends SudokuObjectAbstract> boolean isNumberInBox(T sudokuObject, Integer number, int row, int column) {
        int boxesWidth = sudokuObject.getQuantityBoxesWidth();
        int boxesHeight = sudokuObject.getQuantityBoxesHeight();
        int homeBoxRow = row - row % boxesWidth;
        int homeBoxColum = column - column % boxesHeight;
        for (int i = homeBoxRow; i < homeBoxRow + boxesWidth; i++) {
            for (int j = homeBoxColum; j < homeBoxColum + boxesHeight; j++) {
                if (sudokuObject.getValueFromArray(i, j).equals(number)) {
                    return true;
                }
            }
        }
        return false;
    }

    //This method check all three row/column/box methods
    public <T extends SudokuObjectAbstract> boolean isValidPlacement(T sudokuObject, Integer number, int row, int column) {
        return !isNumberInRow(sudokuObject, number, row) && !isNumberInColum(sudokuObject, number, column) && !isNumberInBox(sudokuObject, number, row, column);
    }

    public <T extends SudokuObjectAbstract> boolean solveBoard(T sudokuObject) {

        for (int row = 0; row < sudokuObject.getSudokuSize(); row++) {
            for (int column = 0; column < sudokuObject.getSudokuSize(); column++) {
                if (sudokuObject.getValueFromArray(row, column).equals(0)) {
                    for (int numberToTry = 1; numberToTry <= sudokuObject.getSudokuSize(); numberToTry++) {
                        if (isValidPlacement(sudokuObject, numberToTry, row, column)) {
                            sudokuObject.setValueToArray(row, column, numberToTry);
                            if (solveBoard(sudokuObject)) {
                                return true;
                            } else {
                                sudokuObject.setValueToArray(row, column, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
