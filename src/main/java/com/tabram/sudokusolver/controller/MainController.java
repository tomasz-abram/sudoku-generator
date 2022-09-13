package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuBoardDto;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.SudokuSolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    SudokuBoardRepository sudokuBoardRepository;
    SudokuSolveService sudokuSolveService;
@Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
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
}
