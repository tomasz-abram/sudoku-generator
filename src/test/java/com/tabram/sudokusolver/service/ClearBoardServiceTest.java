package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClearBoardServiceTest {

    @Mock
    SudokuObjectRepository sudokuObjectRepository;

    ClearBoardService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClearBoardService(sudokuObjectRepository);
    }

    @Nested
    class ClearBoard {
        @Test
        void changeAllValuesInTheBoardToNull() {
            Integer[][] testBoardZero = {
                    {0, 0, 4, 0, 6, 0, 0, 0, 2},
                    {3, 0, 0, 5, 0, 0, 0, 0, 7},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 0, 0, 8, 0, 0, 0, 0},
                    {0, 3, 0, 0, 4, 0, 7, 0, 8},
                    {5, 0, 0, 0, 7, 0, 0, 0, 6},
                    {0, 0, 0, 0, 0, 0, 1, 8, 0},
                    {2, 0, 0, 9, 0, 0, 0, 0, 3},
                    {0, 1, 0, 6, 0, 0, 0, 2, 0}
            };
            SudokuObject testSudokuObject = new SudokuObject(testBoardZero, 9, 3, 3);
            Integer[][] testBoardNull = {
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null, null}
            };
            underTest.clearBoard(testSudokuObject);
            assertAll(
                    () -> assertEquals(9, testSudokuObject.getSudokuSize()),
                    () -> assertEquals(3, testSudokuObject.getQuantityBoxesHeight()),
                    () -> assertEquals(3, testSudokuObject.getQuantityBoxesWidth()),
                    () -> assertThat(testBoardNull).isDeepEqualTo(testSudokuObject.getBoard()));
        }
    }
}