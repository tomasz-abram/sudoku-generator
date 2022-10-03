package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import com.tabram.sudokusolver.service.BoardValueManipulation;
import com.tabram.sudokusolver.service.ClearBoardService;
import com.tabram.sudokusolver.service.GenerateSudokuGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GenerateGameController {
    private static final String HOME = "redirect:/";
    private final GenerateSudokuGameService generateSudokuGameService;
    private final ClearBoardService clearBoardService;
    private final SudokuObjectRepository sudokuObjectRepository;
    private final BoardValueManipulation boardValueManipulation;

    public GenerateGameController(GenerateSudokuGameService generateSudokuGameService, ClearBoardService clearBoardService, SudokuObjectRepository sudokuObjectRepository, BoardValueManipulation boardValueManipulation) {
        this.generateSudokuGameService = generateSudokuGameService;
        this.clearBoardService = clearBoardService;
        this.sudokuObjectRepository = sudokuObjectRepository;
        this.boardValueManipulation = boardValueManipulation;
    }

    @GetMapping("/generate-easy-game")
    public String generateEasyGame() {
        clearBoardService.clearBoard(sudokuObjectRepository.getSudokuObject());
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectRepository.getSudokuObject());
        generateSudokuGameService.generate(sudokuObjectRepository.getSudokuObject());
        return HOME;
    }
}
