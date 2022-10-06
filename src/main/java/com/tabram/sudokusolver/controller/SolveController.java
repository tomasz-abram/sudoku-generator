package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import com.tabram.sudokusolver.repository.TempSudokuObject;
import com.tabram.sudokusolver.service.BoardValueManipulation;
import com.tabram.sudokusolver.service.MapperService;
import com.tabram.sudokusolver.service.SudokuSolveService;
import com.tabram.sudokusolver.validation.CompereWithTempSudokuBoard;
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

    private static final String HOME = "redirect:/";
    private final SudokuObjectRepository sudokuObjectRepository;
    private final SudokuSolveService<SudokuObjectDto> sudokuSolveService;
    private final BoardValueManipulation<SudokuObjectDto> boardValueManipulation;
    private final MapperService mapperService;
    private final TempSudokuObject tempSudokuObject;
    private final CompereWithTempSudokuBoard compereWithTempSudokuBoard;

    public SolveController(SudokuObjectRepository sudokuObjectRepository, SudokuSolveService<SudokuObjectDto> sudokuSolveService, BoardValueManipulation<SudokuObjectDto> boardValueManipulation, MapperService mapperService, TempSudokuObject tempSudokuObject, CompereWithTempSudokuBoard compereWithTempSudokuBoard) {
        this.sudokuObjectRepository = sudokuObjectRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.boardValueManipulation = boardValueManipulation;
        this.mapperService = mapperService;
        this.tempSudokuObject = tempSudokuObject;
        this.compereWithTempSudokuBoard = compereWithTempSudokuBoard;
    }

    @PutMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/home";
        }
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectDto);
       /*
          It checks if the resolved table is in the tempSudokuObject, if so, it gets the solution from tempSudokuObject,
          otherwise it resolves the table.
         */
        if (compereWithTempSudokuBoard.compere(sudokuObjectDto)) {
            SudokuObject sudokuObject = tempSudokuObject.getSudokuObject();
            sudokuObjectRepository.setSudokuObject(sudokuObject);
            tempSudokuObject.setSudokuObject(null);
        } else {
            sudokuSolveService.solveBoard(sudokuObjectDto);
            sudokuObjectRepository.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        }

        return HOME;
    }

    @PutMapping("/solve-cell")
    public String solveCell(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result, @RequestParam("solveCell") String cellId, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/home";
        }
        if (Objects.equals(cellId, "notSelected")) {
            redirectAttributes.addFlashAttribute("errors", "The cell has not been selected! Please select a blank cell to solve");
            return HOME;
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

        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectDto);

        /*
         * This checks if there is already a solution in TempSudokuObject, if solution exists, it gets a value from TempSudokuObject.
         * Otherwise, to get a single cell solution it has to solve the table.
         * After the table is resolved, the table is stored at TempSudokuObject.
         */
        if (compereWithTempSudokuBoard.compere(sudokuObjectDto)) {
            Integer solveCell = tempSudokuObject.getSudokuObject().getValueFromArray(i, j);
            sudokuObjectRepository.getSudokuObject().setValueToArray(i, j, solveCell);
        } else {
            sudokuSolveService.solveBoard(sudokuObjectDto);
            SudokuObject tempBoardObject = mapperService.mapperToSudokuBoardObject(sudokuObjectDto);
            tempSudokuObject.setSudokuObject(tempBoardObject);
            Integer solveCell = tempSudokuObject.getSudokuObject().getValueFromArray(i, j);
            sudokuObjectRepository.getSudokuObject().setValueToArray(i, j, solveCell);
        }
        return HOME;
    }

}
