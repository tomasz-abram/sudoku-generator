package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuBoardObjectDto;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.BoardSizeService;
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

    private static final String HOME = "redirect:/";
    private final SudokuBoardRepository sudokuBoardRepository;
    private final SudokuSolveService sudokuSolveService;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation boardValueManipulation;
    private final BoardSizeService boardSizeService;

    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation, BoardSizeService boardSizeService) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
        this.boardSizeService = boardSizeService;
    }


    @ModelAttribute("sudokuBoardObject")
    public SudokuBoardObjectDto sudokuBoardDto() {
        return new SudokuBoardObjectDto();
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("sudokuBoardObject", sudokuBoardRepository.getSudokuBoardObject());
        return mav;
    }

    @PostMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuBoardObject") SudokuBoardObjectDto sudokuBoardObjectDto) {
        sudokuBoardRepository.setSudokuBoardObject(sudokuBoardObjectDto);
        sudokuSolveService.solveBoard();
        return HOME;
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard();
        return HOME;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize) {
        boardSizeService.generateNewBoard(boardSize);
        return HOME;
    }
}
