package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.service.FileWriteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class InputOutputController {

    private static final String HOME = "redirect:/";
    private final FileWriteService fileWriteService;

    public InputOutputController(FileWriteService fileWriteService) {
        this.fileWriteService = fileWriteService;
    }

    @GetMapping("/save-file")
    public String saveFile() {
        fileWriteService.writeFile();
        return HOME;
    }

}
