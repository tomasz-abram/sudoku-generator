package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final FileIOService fileIOService;


    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation, BoardSizeService boardSizeService, FileIOService fileIOService) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
        this.boardSizeService = boardSizeService;
        this.fileIOService = fileIOService;
    }


    @ModelAttribute("sudokuBoardObject")
    public SudokuBoardObject sudokuBoardDto() {
        return new SudokuBoardObject();
    }

    @ModelAttribute("fileBucket")
    public FileBucket fileBucket() {
        return new FileBucket();
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("sudokuBoardObject", boardValueManipulation.changeZeroToNullOnBoard(sudokuBoardRepository.getSudokuBoardObject()));
        model.addAttribute("fileBucket", new FileBucket());
        return "home";
    }

    @PostMapping("/solve-all")
    public String solveAll(@Valid SudokuBoardObject sudokuBoardObject, BindingResult result) {
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

    @GetMapping("/download-file")
    public ResponseEntity<byte[]> downloadFile() {
        SudokuBoardObject sudokuBoardObject = sudokuBoardRepository.getSudokuBoardObject();
        String sudokuJsonString = fileIOService.exportBoard(sudokuBoardObject);
        byte[] sudokuJsonBytes = sudokuJsonString.getBytes();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sudokuBoard.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(sudokuJsonBytes.length)
                .body(sudokuJsonBytes);
    }

    @PostMapping("/upload-file")
    public String uploadFile(@Valid FileBucket fileBucket, BindingResult result) {

        if (result.hasErrors()) {
            return "/home";
        }

        return HOME;
    }

}

