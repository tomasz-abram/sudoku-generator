package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.service.FileIOService;
import com.tabram.sudokusolver.service.SudokuObjectService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/", "/home"})
public class FileIOController {

    private static final String REDIRECT = "redirect:/";
    private final FileIOService fileIOService;
    private final SudokuObjectService sudokuObjectService;


    public FileIOController(FileIOService fileIOService, SudokuObjectService sudokuObjectService) {
        this.fileIOService = fileIOService;
        this.sudokuObjectService = sudokuObjectService;
    }

    @GetMapping("/download-file")
    public ResponseEntity<byte[]> downloadFile() {
        SudokuObject sudokuObject = sudokuObjectService.getSudokuObject();
        String sudokuJsonString = fileIOService.exportSudokuObject(sudokuObject);
        byte[] sudokuJsonBytes = sudokuJsonString.getBytes();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sudokuObject.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(sudokuJsonBytes.length)
                .body(sudokuJsonBytes);
    }

    @PostMapping("/upload-file")
    public String uploadFile(@Valid FileBucket fileBucket, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        } else {
            MultipartFile file = fileBucket.getFile();
            fileIOService.importSudokuObject(file);
            redirectAttributes.addFlashAttribute("messages", "You successfully uploaded " + file.getOriginalFilename() + "!");
        }
        return REDIRECT;
    }

}



