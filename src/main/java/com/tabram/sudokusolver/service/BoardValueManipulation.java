package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardValueManipulation {

    SudokuBoardRepository sudokuBoardRepository;

    @Autowired
    public BoardValueManipulation(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    public List<ArrayList<Integer>> changeNullToZeroOnBoard(List<ArrayList<Integer>> sudokuBoard) {
        for (int row = 0; row < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); row++) {
            for (int column = 0; column < sudokuBoardRepository.getSudokuBoard().getSudokuSize(); column++) {
                if (sudokuBoard.get(row).get(column) == null) {
                    Integer zero = 0;
                    sudokuBoard.get(row).set(column, zero);
                }
            }
        }
        return sudokuBoard;
    }

    public int[][] convert2DArrayListTo2DArray(List<ArrayList<Integer>> sudokuBoard) {
        //Solution created by Pimp Trizkit and published on stackoverflow.com
        return sudokuBoard.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);
    }

}

