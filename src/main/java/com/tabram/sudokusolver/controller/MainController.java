package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.SudokuBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    SudokuBoard sudokuBoard;

    @Autowired
    public MainController(SudokuBoard sudokuBoard){
        this.sudokuBoard = sudokuBoard;
    }

    @GetMapping({"/", "/home"})
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("board", sudokuBoard.getBoard());
        return mav;
    }

}
