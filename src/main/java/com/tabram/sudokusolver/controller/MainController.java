package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.dto.SudokuBoardObjectDto;
import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    private static final String HOME = "redirect:/";
    private final SudokuBoardRepository sudokuBoardRepository;
    private final SudokuSolveService sudokuSolveService;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation boardValueManipulation;
    private final BoardSizeService boardSizeService;
    private final MapperService mapperService;

    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation, BoardSizeService boardSizeService, MapperService mapperService) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
        this.boardSizeService = boardSizeService;
        this.mapperService = mapperService;
    }


    @ModelAttribute("sudokuBoardObject")
    public SudokuBoardObjectDto sudokuBoardDto() {
        return new SudokuBoardObjectDto();
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("sudokuBoardObject", boardValueManipulation.changeZeroToNullOnBoard(sudokuBoardRepository.getSudokuBoardObject()));
        model.addAttribute("fileBucket", new FileBucket());
        return "home";
    }

    @PutMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuBoardObject") @Valid SudokuBoardObjectDto sudokuBoardObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/home";
        }
        SudokuBoardObjectDto sudokuBoardObjectWithZero = boardValueManipulation.changeNullToZeroOnBoard(sudokuBoardObjectDto);
        sudokuBoardRepository.setSudokuBoardObject(mapperService.mapperToSudokuBoardObject(sudokuBoardObjectWithZero));
        sudokuSolveService.solveBoard();
        return HOME;
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("sudokuBoardObject") @Valid SudokuBoardObjectDto sudokuBoardObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/home";
        }
        SudokuBoardObjectDto sudokuBoardObjectWithZero = boardValueManipulation.changeNullToZeroOnBoard(sudokuBoardObjectDto);
        sudokuBoardRepository.setSudokuBoardObject(mapperService.mapperToSudokuBoardObject(sudokuBoardObjectWithZero));
        return HOME;
    }

    @PostMapping("/solve-cell")
    public String solveCell(@RequestParam("solveCell") String cellId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cellId);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        return HOME;
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        SudokuBoardObject sudokuBoardObject = clearBoardService.clearBoard(sudokuBoardRepository.getSudokuBoardObject());
        sudokuBoardRepository.setSudokuBoardObject(sudokuBoardObject);
        return HOME;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize) {
        boardSizeService.generateNewBoard(boardSize);
        return HOME;
    }


}

