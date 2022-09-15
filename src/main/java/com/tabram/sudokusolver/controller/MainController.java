package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuBoardDto;
import com.tabram.sudokusolver.model.SudokuBoard;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import com.tabram.sudokusolver.service.BoardSizeService;
import com.tabram.sudokusolver.service.BoardValueManipulation;
import com.tabram.sudokusolver.service.ClearBoardService;
import com.tabram.sudokusolver.service.SudokuSolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Controller
@RequestMapping({"/", "/home"})
public class MainController {

    private static final String HOME = "redirect:/";
    private final SudokuBoardRepository sudokuBoardRepository;
    private final SudokuSolveService sudokuSolveService;
    private final ClearBoardService clearBoardService;
    private final BoardValueManipulation boardValueManipulation;
    private final BoardSizeService boardSizeService;

    @Autowired
    public MainController(SudokuBoardRepository sudokuBoardRepository, SudokuSolveService sudokuSolveService, ClearBoardService clearBoardService, BoardValueManipulation boardValueManipulation, BoardSizeService boardSizeService) {
        this.sudokuBoardRepository = sudokuBoardRepository;
        this.sudokuSolveService = sudokuSolveService;
        this.clearBoardService = clearBoardService;
        this.boardValueManipulation = boardValueManipulation;
        this.boardSizeService = boardSizeService;
    }


    @ModelAttribute("sudokuBoard")
    public SudokuBoardDto sudokuBoardDto() {
        return new SudokuBoardDto();
    }

    @GetMapping
    public ModelAndView home() {
//        int[][] i = sudokuBoardRepository.getSudokuBoard().getBoard();
//        Integer[][] result = Stream.of(i)
//                .map(array -> IntStream.of(array).boxed().toArray(Integer[]::new))
//                .toArray(Integer[][]::new);
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("sudokuBoard", sudokuBoardRepository.getSudokuBoard());
        return mav;
    }

    @PostMapping("/solve-all")
    public String solveAll(@ModelAttribute("sudokuBoard") SudokuBoardDto sudokuBoardDto) {
//        Integer[][] i = boardValueManipulation.changeNullToZeroOnBoard(sudokuBoardDto).getBoard();

        sudokuSolveService.solveBoard(sudokuBoardDto.getBoard().clone());

//        SudokuBoard board = sudokuBoardRepository.getSudokuBoard();
//        board.setBoard(sudokuBoardDto.getBoard().clone());
//sudokuBoardRepository.setSudokuBoard(board);;
//        sudokuSolveService.solveBoard(sudokuBoardDto().getBoard());
        return HOME;
    }

    @DeleteMapping("/clear")
    public String clearBoard() {
        clearBoardService.clearBoard();
        return HOME;
    }

    @PutMapping("/change-board-size")
    public String changeBoardSize(@RequestParam("changeBoardSize") Integer boardSize){
        boardSizeService.generateNewBoard(boardSize);
        return HOME;
    }
}
