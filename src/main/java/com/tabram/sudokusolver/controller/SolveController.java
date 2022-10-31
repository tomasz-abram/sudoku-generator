package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.service.SudokuSolveService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class SolveController {

    private static final String REDIRECT = "redirect:/";
    private final SudokuSolveService sudokuSolveService;

    public SolveController(SudokuSolveService sudokuSolveService) {
        this.sudokuSolveService = sudokuSolveService;
    }

    @PutMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "home";
        }
        sudokuSolveService.solveAll(sudokuObjectDto);
        return REDIRECT;
    }

    @PutMapping("/solve-cell")
    public String solveCell(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result, @RequestParam("solveCell") String cellId, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "home";
        }
        if (Objects.equals(cellId, "notSelected")) {
            redirectAttributes.addFlashAttribute("errors", "The cell has not been selected! Please select a blank cell to solve");
            return REDIRECT;
        }
        sudokuSolveService.solveCell(sudokuObjectDto, cellId);
        return REDIRECT;
    }

}
