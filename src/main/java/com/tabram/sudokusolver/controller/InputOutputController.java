package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.JsonExporterService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/", "/home"})
public class InputOutputController {

    private static final String HOME = "redirect:/";

    private final JsonExporterService jsonExporterService;

    private final SudokuBoardRepository sudokuBoardRepository;


    public InputOutputController(JsonExporterService jsonExporterService, SudokuBoardRepository sudokuBoardRepository) {
        this.jsonExporterService = jsonExporterService;
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    @GetMapping("/download-file")
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

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");
        return HOME;
    }

}



