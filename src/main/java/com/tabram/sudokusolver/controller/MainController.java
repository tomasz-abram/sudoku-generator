package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    private static final String REDIRECT = "redirect:/";
    private static final String HOME = "home";
    private final SudokuObjectService sudokuObjectService;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulationService<SudokuObjectAbstract> boardValueManipulationService;
    private final BoardSizeService boardSizeService;
    private final MapperService mapperService;

    @Autowired
    public MainController(SudokuObjectService sudokuObjectService, ClearBoardService clearBoardService, BoardValueManipulationService<SudokuObjectAbstract> boardValueManipulationService, BoardSizeService boardSizeService, MapperService mapperService) {
        this.sudokuObjectService = sudokuObjectService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulationService = boardValueManipulationService;
        this.boardSizeService = boardSizeService;
        this.mapperService = mapperService;
    }

    @ModelAttribute("sudokuObject")
    public SudokuObjectDto sudokuObjectDto() {
        return new SudokuObjectDto();
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("sudokuObject", boardValueManipulationService.changeZeroToNullOnBoard(sudokuObjectService.getSudokuObject()));
        model.addAttribute("fileBucket", new FileBucket());
        return "home";
    }

    @PutMapping("/save")
    public String save(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return HOME;
        }
        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObjectDto);
        sudokuObjectService.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        return REDIRECT;
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard(sudokuObjectService.getSudokuObject());
        return REDIRECT;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize) {
        SudokuObject sudokuObject = boardSizeService.generateNewBoard(boardSize);
        sudokuObjectService.setSudokuObject(sudokuObject);
        return REDIRECT;
    }

    @PutMapping("/check")
    public String check(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return HOME;
        }
        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObjectDto);
        sudokuObjectService.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        return REDIRECT;
    }
}

