package com.tabram.sudokusolver.service;


import com.tabram.sudokusolver.model.SudokuObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class BoardSizeServiceTest {

    private BoardSizeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BoardSizeService();
    }

    @Test
    void generateNewBoard() {
        //given
        int sudokuSize = 18;
        Integer[][] testBoard = new Integer[18][18];
        SudokuObject testSudokuObject = new SudokuObject(new Integer[sudokuSize][sudokuSize], sudokuSize, 6, 3);
        //when
        SudokuObject sudokuActual = underTest.generateNewBoard(sudokuSize);
        //then
        assertAll(
                () -> assertEquals(sudokuSize, sudokuActual.getSudokuSize()),
                () -> assertEquals(6, sudokuActual.getQuantityBoxesHeight()),
                () -> assertEquals(3, sudokuActual.getQuantityBoxesWidth()),
                () -> assertThat(testBoard).isDeepEqualTo(sudokuActual.getBoard()));
    }

    @Test
    void selectsTheClosestNumbersToCreateSudokuBox() {
        //given
        int testN = 18;
        List<Integer> testList = new ArrayList<>();
        testList.add(2);
        testList.add(3);
        testList.add(6);
        testList.add(9);
        //when
        Map<String, Integer> actualMap = underTest.smallBoxSize(testList, testN);
        //then
        assertAll(
                () -> assertEquals(3, actualMap.get("width")),
                () -> assertEquals(6, actualMap.get("height")));
    }

    @Nested
    class DivisorsList {
        @Test
        void generateListOfDivisorsOfGivenNumber() {
            //given
            int testN = 18;
            List<Integer> testList = new ArrayList<>();
            testList.add(2);
            testList.add(3);
            testList.add(6);
            testList.add(9);
            //when
            List<Integer> actualDivisorsList = underTest.divisorsList(testN);
            //then
            assertThat(testList).isEqualTo(actualDivisorsList);
        }

        @Test
        void whenTheGivenNumberCannotBeUsedToCreateASudokuTable() {
            int testN = 2;
            Assertions.assertThatThrownBy(
                            () -> underTest.divisorsList(testN))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("You cannot create a sudoku of this size. " + "(" + testN + ")");

        }
    }
}