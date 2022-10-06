package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BoardValueManipulationTest {

    private BoardValueManipulation<SudokuObject> underTest;

    @BeforeEach
    void setUp() {
        underTest = new BoardValueManipulation<>();
    }

    @Nested
    class ChangeNullToZeroOnBoard {
        @Test
        void changeNullToZeroOnTheBoardInTheGivenSudokuObject() {
            Integer[][] testBoardNull = {
                    {null, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuObject testSudokuObjectNull = new SudokuObject(testBoardNull, 9, 3, 3);
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
            //when
            underTest.changeNullToZeroOnBoard(testSudokuObjectNull);
            assertAll(
                    () -> assertEquals(9, testSudokuObjectNull.getSudokuSize()),
                    () -> assertEquals(3, testSudokuObjectNull.getQuantityBoxesHeight()),
                    () -> assertEquals(3, testSudokuObjectNull.getQuantityBoxesWidth()));
            assertThat(testBoardZero).isDeepEqualTo(testSudokuObjectNull.getBoard());
        }
    }

    @Nested
    class ChangeZeroToNullOnBoard {
        @Test
        void changeZeroToNullOnTheBoardInTheGivenSudokuObject() {
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
            SudokuObject testSudokuObjectZero = new SudokuObject(testBoardZero, 9, 3, 3);
            Integer[][] testBoardNull = {
                    {null, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };

            SudokuObjectAbstract sudokuActual = underTest.changeZeroToNullOnBoard(testSudokuObjectZero);
            assertAll(
                    () -> assertEquals(testSudokuObjectZero.getSudokuSize(), sudokuActual.getSudokuSize()),
                    () -> assertEquals(testSudokuObjectZero.getQuantityBoxesHeight(), sudokuActual.getQuantityBoxesHeight()),
                    () -> assertEquals(testSudokuObjectZero.getQuantityBoxesWidth(), sudokuActual.getQuantityBoxesWidth()));
            assertThat(testBoardNull).isDeepEqualTo(sudokuActual.getBoard());
        }
    }
}