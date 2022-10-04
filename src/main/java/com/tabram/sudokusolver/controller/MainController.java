package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.dto.SudokuObjectDto;
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

    private static final String HOME = "redirect:/";
    private final SudokuObjectRepository sudokuObjectRepository;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation boardValueManipulation;
    private final BoardSizeService boardSizeService;
    private final MapperService mapperService;

    @Autowired
    public MainController(SudokuObjectRepository sudokuObjectRepository, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation, BoardSizeService boardSizeService, MapperService mapperService) {
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
            return "/home";
        }
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectDto);
        sudokuObjectRepository.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        return HOME;
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard(sudokuObjectRepository.getSudokuObject());
        return HOME;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize) {
        boardSizeService.generateNewBoard(boardSize);
        return HOME;
    }
}

