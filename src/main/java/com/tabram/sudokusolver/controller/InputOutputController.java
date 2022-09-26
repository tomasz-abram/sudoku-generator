package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.JsonExporterService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class InputOutputController {

    private final JsonExporterService jsonExporterService;

    private final SudokuBoardRepository sudokuBoardRepository;

    public InputOutputController(JsonExporterService jsonExporterService, SudokuBoardRepository sudokuBoardRepository) {

        this.jsonExporterService = jsonExporterService;
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    @GetMapping("/save-file")
    public ResponseEntity<byte[]> saveFile() {

        SudokuBoardObject sudokuBoardObject = sudokuBoardRepository.getSudokuBoardObject();

        String sudokuJsonString = jsonExporterService.export(sudokuBoardObject);

        byte[] sudokuJsonBytes = sudokuJsonString.getBytes();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sudokuBoard.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(sudokuJsonBytes.length)
                .body(sudokuJsonBytes);
    }
}



