package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import com.tabram.sudokusolver.service.BoardValueManipulation;
import com.tabram.sudokusolver.service.ClearBoardService;
import com.tabram.sudokusolver.service.GenerateSudokuGameService;
import com.tabram.sudokusolver.service.SudokuSolveService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GenerateGameController {
    private static final String HOME = "redirect:/";
    private final GenerateSudokuGameService generateSudokuGameService;
    private final ClearBoardService clearBoardService;
    private final SudokuObjectRepository sudokuObjectRepository;
    private final BoardValueManipulation boardValueManipulation;
    private final SudokuSolveService sudokuSolveService;


    public GenerateGameController(GenerateSudokuGameService generateSudokuGameService, ClearBoardService clearBoardService, SudokuObjectRepository sudokuObjectRepository, BoardValueManipulation boardValueManipulation, SudokuSolveService sudokuSolveService) {
        this.generateSudokuGameService = generateSudokuGameService;
        this.clearBoardService = clearBoardService;
        this.sudokuObjectRepository = sudokuObjectRepository;
        this.boardValueManipulation = boardValueManipulation;
        this.sudokuSolveService = sudokuSolveService;

    }

    @GetMapping("/generate-easy-game")
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
                percent = 0;
        }
        SudokuObject sudokuObject = sudokuObjectRepository.getSudokuObject();
        clearBoardService.clearBoard(sudokuObject);
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObject);
        generateSudokuGameService.generate(sudokuObject);
        sudokuSolveService.solveBoard(sudokuObject);
        generateSudokuGameService.randomCleanBoard(sudokuObject, percent);
        return HOME;
    }
}
