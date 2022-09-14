package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuBoardDto;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.BoardValueManipulation;
import com.tabram.sudokusolver.service.ClearBoardService;
import com.tabram.sudokusolver.service.SudokuSolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    private final SudokuBoardRepository sudokuBoardRepository;
    private final SudokuSolveService sudokuSolveService;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation boardValueManipulation;

    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
    }


    @ModelAttribute("sudokuBoard")
    public SudokuBoardDto sudokuBoardDto() {
        return new SudokuBoardDto();
    }

    @GetMapping
    public ModelAndView home() {

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("sudokuBoard", boardValueManipulation.changeZeroToNullOnBoard(sudokuBoardRepository.getSudokuBoard()));
        return mav;
    }

    @PostMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuBoard") SudokuBoardDto sudokuBoardDto) {
        sudokuSolveService.solveBoard(boardValueManipulation.changeNullToZeroOnBoard(sudokuBoardDto).getBoard());
        return "redirect:/";
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard();
        return "redirect:/";
    }
}
