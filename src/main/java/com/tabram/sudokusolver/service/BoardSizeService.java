package com.tabram.sudokusolver.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardSizeService {

    public List<Integer> divisorsList(int n) {
        List<Integer> divisionList = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (n % i == 0 && i != n) {
                divisionList.add(i);
            }
        }
        if(divisionList.isEmpty()){
            throw new IllegalArgumentException("You cannot create a sudoku of this size. " +"(" + n + ")");
        }
        return divisionList;
    }

    public void smallBoxSize(List<Integer> divisionList, int n) {
        Integer temp1 = 0;
        Integer temp2 = 0;
        Integer ratio = -1;

        for (int i = 0; i < divisionList.size(); i++) {
            Integer a = divisionList.get(i);
            for (int j = 0; j < divisionList.size(); j++) {
                Integer b = divisionList.get(j);
                if (a * b == n) {
                    System.out.println("Found: " + a + " and " + b);
                    if (ratio > b - a && b - a >= 0 || ratio < 0) {
                        temp1 = a;
                        temp2 = b;
                        ratio = b - a;
                    }
                }
            }
            System.out.println("Numbers " + temp1 + " " + temp2 + " Ratio: " + ratio);
        }
    }
}
