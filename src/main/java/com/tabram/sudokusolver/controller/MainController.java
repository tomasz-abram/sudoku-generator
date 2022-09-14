package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuBoardDto;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
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

    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
    }


    @ModelAttribute("sudokuBoard")
    public SudokuBoardDto sudokuBoardDto() {
        return new SudokuBoardDto();
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("sudokuBoard", sudokuBoardRepository.getSudokuBoard());
        return mav;
    }

    @PostMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuBoard") SudokuBoardDto sudokuBoardDto) {
        sudokuSolveService.solveBoard(sudokuBoardDto.getBoard());
        return "redirect:/";
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard();
        return "redirect:/";
    }
}
