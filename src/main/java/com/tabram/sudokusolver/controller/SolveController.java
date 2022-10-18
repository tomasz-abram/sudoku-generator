package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.service.*;
import com.tabram.sudokusolver.validation.CompareWithTempSudokuBoardValidation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SolveController {

    private static final String REDIRECT = "redirect:/";
    private final SudokuObjectService sudokuObjectService;
    private final SudokuSolveService<SudokuObjectDto> sudokuSolveService;
    private final BoardValueManipulationService<SudokuObjectDto> boardValueManipulationService;
    private final MapperService mapperService;
    private final TempSudokuObjectService tempSudokuObjectService;
    private final CompareWithTempSudokuBoardValidation compareWithTempSudokuBoardValidation;

    public SolveController(SudokuObjectService sudokuObjectService, SudokuSolveService<SudokuObjectDto> sudokuSolveService, BoardValueManipulationService<SudokuObjectDto> boardValueManipulationService, MapperService mapperService, TempSudokuObjectService tempSudokuObjectService, CompareWithTempSudokuBoardValidation compareWithTempSudokuBoardValidation) {
        this.sudokuObjectService = sudokuObjectService;
        this.sudokuSolveService = sudokuSolveService;
        this.boardValueManipulationService = boardValueManipulationService;
        this.mapperService = mapperService;
        this.tempSudokuObjectService = tempSudokuObjectService;
        this.compareWithTempSudokuBoardValidation = compareWithTempSudokuBoardValidation;
    }

    @PutMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "home";
        }
        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObjectDto);
       /*
          It checks if the resolved table is in the tempSudokuObject, if so, it gets the solution from tempSudokuObject,
          otherwise it resolves the table.
         */
        if (compareWithTempSudokuBoardValidation.compare(sudokuObjectDto)) {
            SudokuObject sudokuObject = tempSudokuObjectService.getSudokuObject();
            sudokuObjectService.setSudokuObject(sudokuObject);
            tempSudokuObjectService.setSudokuObject(null);
        } else {
            sudokuSolveService.solveBoard(sudokuObjectDto);
            sudokuObjectService.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        }
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
        /*
          This gets information about the selected cell.
         */
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cellId);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        int i = Integer.parseInt(list.get(0));
        int j = Integer.parseInt(list.get(1));

        boardValueManipulationService.changeNullToZeroOnBoard(sudokuObjectDto);
        /*
         * This checks if there is already a solution in TempSudokuObject, if solution exists, it gets a value from TempSudokuObject.
         * Otherwise, to get a single cell solution it has to solve the table.
         * After the table is resolved, the table is stored at TempSudokuObject.
         */
        if (compareWithTempSudokuBoardValidation.compare(sudokuObjectDto)) {
            Integer solveCell = tempSudokuObjectService.getSudokuObject().getValueFromArray(i, j);
            sudokuObjectService.getSudokuObject().setValueToArray(i, j, solveCell);
        } else {
            sudokuSolveService.solveBoard(sudokuObjectDto);
            SudokuObject tempBoardObject = mapperService.mapperToSudokuBoardObject(sudokuObjectDto);
            tempSudokuObjectService.setSudokuObject(tempBoardObject);
            Integer solveCell = tempSudokuObjectService.getSudokuObject().getValueFromArray(i, j);
            sudokuObjectService.getSudokuObject().setValueToArray(i, j, solveCell);
        }
        return REDIRECT;
    }

}
