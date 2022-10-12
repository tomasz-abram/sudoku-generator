package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import com.tabram.sudokusolver.service.BoardSizeService;
import com.tabram.sudokusolver.service.BoardValueManipulation;
import com.tabram.sudokusolver.service.ClearBoardService;
import com.tabram.sudokusolver.service.MapperService;
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
    private final SudokuObjectRepository sudokuObjectRepository;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation<com.tabram.sudokusolver.model.SudokuObjectAbstract> boardValueManipulation;
    private final BoardSizeService boardSizeService;
    private final MapperService mapperService;

    @Autowired
    public MainController(SudokuObjectRepository sudokuObjectRepository, ClearBoardService clearBoardService, BoardValueManipulation<com.tabram.sudokusolver.model.SudokuObjectAbstract> boardValueManipulation, BoardSizeService boardSizeService, MapperService mapperService) {
        this.sudokuObjectRepository = sudokuObjectRepository;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
        this.boardSizeService = boardSizeService;
        this.mapperService = mapperService;
    }

    @ModelAttribute("sudokuObject")
    public SudokuObjectDto sudokuObjectDto() {
        return new SudokuObjectDto();
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("sudokuObject", boardValueManipulation.changeZeroToNullOnBoard(sudokuObjectRepository.getSudokuObject()));
        model.addAttribute("fileBucket", new FileBucket());
        return "home";
    }

    @PutMapping("/save")
    public String save(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return HOME;
        }
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectDto);
        sudokuObjectRepository.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        return REDIRECT;
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard(sudokuObjectRepository.getSudokuObject());
        return REDIRECT;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize) {
        SudokuObject sudokuObject = boardSizeService.generateNewBoard(boardSize);
        sudokuObjectRepository.setSudokuObject(sudokuObject);
        return REDIRECT;
    }

    @PutMapping("/check")
    public String check(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return HOME;
        }
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectDto);
        sudokuObjectRepository.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        return REDIRECT;
    }
}

