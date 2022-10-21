package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.service.GenerateSudokuGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GenerateGameController {
    private static final String REDIRECT = "redirect:/";
    private final GenerateSudokuGameService generateSudokuGameService;

    public GenerateGameController(GenerateSudokuGameService generateSudokuGameService) {
        this.generateSudokuGameService = generateSudokuGameService;

    }

    @GetMapping("/generate-game")
    public String generateGame(@RequestParam String level, RedirectAttributes redirectAttributes) {
        if (level.equals("Easy") || level.equals("Medium") || level.equals("Hard")) {
            generateSudokuGameService.generateGame(level);
        } else {
            redirectAttributes.addFlashAttribute("errors", "The level has not been selected");
        }
        return REDIRECT;
    }
}
