package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.FileBucket;
import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import com.tabram.sudokusolver.repository.TempSudokuObject;
import com.tabram.sudokusolver.service.*;
import com.tabram.sudokusolver.validation.CompereWithTempSudokuBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    private static final String HOME = "redirect:/";
    private final SudokuObjectRepository sudokuObjectRepository;
    private final SudokuSolveService sudokuSolveService;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation boardValueManipulation;
    private final BoardSizeService boardSizeService;
    private final MapperService mapperService;
    private final TempSudokuObject tempSudokuObject;
    private final CompereWithTempSudokuBoard compereWithTempSudokuBoard;

    @Autowired
    public MainController(SudokuObjectRepository sudokuObjectRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation, BoardSizeService boardSizeService, MapperService mapperService, TempSudokuObject tempSudokuObject, CompereWithTempSudokuBoard compereWithTempSudokuBoard) {
        this.sudokuObjectRepository = sudokuObjectRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
        this.boardSizeService = boardSizeService;
        this.mapperService = mapperService;
        this.tempSudokuObject = tempSudokuObject;
        this.compereWithTempSudokuBoard = compereWithTempSudokuBoard;
    }


    @ModelAttribute("sudokuObject")
    public SudokuObjectDto sudokuObjectDto() {
        return new SudokuObjectDto();
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("sudokuObject", boardValueManipulation.changeZeroToNullOnBoard(sudokuObjectRepository.getSudokuObject()));
        model.addAttribute("fileBucket", new FileBucket());
        return "home";
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

    @PutMapping("/save")
    public String save(@ModelAttribute("sudokuObject") @Valid SudokuObjectDto sudokuObjectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "/home";
        }
        boardValueManipulation.changeNullToZeroOnBoard(sudokuObjectDto);
        sudokuObjectRepository.setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
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

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard(sudokuObjectRepository.getSudokuObject());
        return HOME;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize) {
        boardSizeService.generateNewBoard(boardSize);
        return HOME;
    }


}

