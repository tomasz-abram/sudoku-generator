package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardSizeService {

    public SudokuObject generateNewBoard(int size) {
        Integer[][] newBoard = new Integer[size][size];
        Map<String, Integer> dim = smallBoxSize(divisorsList(size), size);
        return new SudokuObject(newBoard, size, dim.get("height"), dim.get("width"));
    }


    //    This method generates a list of the divisors of the given number except one and the given number.
    public List<Integer> divisorsList(int n) {
        List<Integer> divisionList = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (n % i == 0 && i != n) {
                divisionList.add(i);
            }
        }
        if (divisionList.isEmpty()) {
            throw new IllegalArgumentException("You cannot create a sudoku of this size. " + "(" + n + ")");
        }
        return divisionList;
    }

    // This method finds the nearest divisors that can form a sudoku box with the shorter side always at the top.
    public Map<String, Integer> smallBoxSize(List<Integer> divisionList, int n) {
        int temp1 = 0;
        int temp2 = 0;
        int ratio = -1;
        Map<String, Integer> dimensions = new HashMap<>();
        for (int i = 0; i < divisionList.size(); i++) {
            Integer a = divisionList.get(i);
            for (Integer b : divisionList) {
                if (a * b == n && (ratio > b - a && b - a >= 0 || ratio < 0)) {
                    temp1 = a;
                    temp2 = b;
                    ratio = b - a;
                }
            }
            dimensions.put("width", temp1);
            dimensions.put("height", temp2);
        }
        return dimensions;
    }
}
