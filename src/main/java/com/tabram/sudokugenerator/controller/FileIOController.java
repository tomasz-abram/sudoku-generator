package com.tabram.sudokugenerator.controller;

import com.tabram.sudokugenerator.dto.FileBucket;
import com.tabram.sudokugenerator.service.FileIOService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/", "/home"})
public class FileIOController {

    private static final String REDIRECT = "redirect:/";
    private final FileIOService fileIOService;

    public FileIOController(FileIOService fileIOService) {
        this.fileIOService = fileIOService;
    }

    @GetMapping("/download-file")
    public ResponseEntity<byte[]> downloadFile() {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sudokuObject.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(fileIOService.downloadFile().length)
                .body(fileIOService.downloadFile());
    }

    @PostMapping("/upload-file")
    public String uploadFile(@Valid FileBucket fileBucket, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        } else {
            fileIOService.importSudokuObject(fileBucket.getFile());
            redirectAttributes.addFlashAttribute("messages", "You successfully uploaded " + fileBucket.getFile().getOriginalFilename() + "!");
        }
        return REDIRECT;
    }

}



