package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.dto.SudokuBoardDto;
import com.tabram.sudokusolver.model.SudokuBoard;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BoardValueManipulation {

    SudokuBoardRepository sudokuBoardRepository;

    @Autowired
    public BoardValueManipulation(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

//    public SudokuBoardDto changeNullToZeroOnBoard(SudokuBoardDto sudokuBoardDto) {
//       Integer [][] sudokuBoard= sudokuBoardDto.getBoard();
//        for (int row = 0; row < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); row++) {
//            for (int column = 0; column < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); column++) {
//                if (Objects.equals(sudokuBoard[row][column], null)) {
//                    sudokuBoard[row][column] = 0;
//                }
//            }
//        }
//        sudokuBoardDto.setBoard(sudokuBoard);
//        return sudokuBoardDto;
//    }

    public Integer[][] changeZeroToNullOnBoard(Integer[][] sudokuBoard) {

        for (int row = 0; row < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); row++) {
            for (int column = 0; column < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); column++) {
                if (Objects.equals(sudokuBoard[row][column], 0)) {
                    sudokuBoard[row][column] = null;
                }
            }
        }
        return sudokuBoard;
    }

}

