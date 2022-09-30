package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
    public SudokuBoardObject sudokuBoardDto() {
        return new SudokuBoardObject();
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("sudokuBoardObject", boardValueManipulation.changeZeroToNullOnBoard(sudokuBoardRepository.getSudokuBoardObject()));
        model.addAttribute("fileBucket", new FileBucket());
        return "home";
    }

    @PostMapping("/solve-all")
    public String solveAll(@Valid SudokuBoardObject sudokuBoardObject, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/home";
        }
        SudokuBoardObject sudokuBoardObjectWithZero = boardValueManipulation.changeNullToZeroOnBoard(sudokuBoardObject);
        sudokuBoardRepository.setSudokuBoardObject(sudokuBoardObjectWithZero);
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

