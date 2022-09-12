package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    SudokuBoardRepository sudokuBoardRepository;

    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository){
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("board", sudokuBoardRepository.getSudokuBoard().getBoard());
        return mav;
    }

    @GetMapping("/solve-all")
    public ModelAndView solveAll() {
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }
}
