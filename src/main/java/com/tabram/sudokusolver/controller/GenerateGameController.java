package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenerateGameController {
    private static final String REDIRECT = "redirect:/";
    private final GenerateSudokuGameService<SudokuObject> generateSudokuGameService;
    private final ClearBoardService clearBoardService;
    private final SudokuObjectService sudokuObjectService;
    private final BoardValueManipulationService<SudokuObject> boardValueManipulationService;
    private final SudokuSolveService<com.tabram.sudokusolver.model.SudokuObjectAbstract> sudokuSolveService;
    private final CopyObjectService copyObjectService;
    private final TempSudokuObjectService tempSudokuObjectService;

    public GenerateGameController(GenerateSudokuGameService<SudokuObject> generateSudokuGameService, ClearBoardService clearBoardService, SudokuObjectService sudokuObjectService, BoardValueManipulationService<SudokuObject> boardValueManipulationService, SudokuSolveService<SudokuObjectAbstract> sudokuSolveService, CopyObjectService copyObjectService, TempSudokuObjectService tempSudokuObjectService) {
        this.generateSudokuGameService = generateSudokuGameService;
        this.clearBoardService = clearBoardService;
        this.sudokuObjectService = sudokuObjectService;
        this.boardValueManipulationService = boardValueManipulationService;
        this.sudokuSolveService = sudokuSolveService;
        this.copyObjectService = copyObjectService;
        this.tempSudokuObjectService = tempSudokuObjectService;
    }


    @GetMapping("/generate-game")
    public String generateGame(@RequestParam String level) {
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
                return REDIRECT;
        }
        SudokuObject sudokuObject = sudokuObjectService.getSudokuObject();
        clearBoardService.clearBoard(sudokuObject);
        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObject);
        generateSudokuGameService.generateNumbersInDiagonalBoxes(sudokuObject);
        sudokuSolveService.solveBoard(sudokuObject);
        tempSudokuObjectService.setSudokuObject(copyObjectService.deepCopy(sudokuObject));
        generateSudokuGameService.randomCleanBoard(sudokuObject, percent);
        return REDIRECT;
    }
}
