package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.model.SudokuObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateSudokuGameServiceTest {

    private final Random random = new Random();
    @Mock
    SudokuSolveService sudokuSolveService;
    @Mock
    private ClearBoardService clearBoardService;
    @Mock
    private SudokuObjectService sudokuObjectService;
    @Spy
    private BoardValueManipulationService<SudokuObject> boardValueManipulationService;
    @Mock
    private CopyObjectService copyObjectService;
    @Mock
    private TempSudokuObjectService tempSudokuObjectService;
    @InjectMocks
    private GenerateSudokuGameService underTest;

    @Nested
    class GenerateGame {
        @ParameterizedTest
        @ValueSource(strings = {"Easy", "Medium", "Hard"})
        void thisShouldGenerateGameDependingOnTheChosenDifficultyLevel(String input) {
            Integer[][] boardNull = {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
            SudokuObject testSudokuObject = new SudokuObject(boardNull, 9, 3, 3);
            when(sudokuObjectService.getSudokuObject()).thenReturn(testSudokuObject);
            when(sudokuSolveService.isValidPlacement(any(), anyInt(), anyInt(), anyInt())).thenReturn(true);
            when(sudokuSolveService.solveBoard(testSudokuObject)).thenCallRealMethod();

            underTest.generateGame(input);

            verify(sudokuObjectService, times(1)).getSudokuObject();
            verify(clearBoardService, times(1)).clearBoard(testSudokuObject);
            verify(boardValueManipulationService, times(1)).changeNullToZeroOnBoard(testSudokuObject);
            verify(tempSudokuObjectService, times(1)).setSudokuObject(copyObjectService.deepCopy(testSudokuObject));
        }


        @Test
        void whenTheOptionsNotSelected_Return302() {
            Integer[][] boardNull = {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
            SudokuObject testSudokuObject = new SudokuObject(boardNull, 9, 3, 3);
            when(sudokuObjectService.getSudokuObject()).thenReturn(testSudokuObject);
            when(sudokuSolveService.isValidPlacement(any(), anyInt(), anyInt(), anyInt())).thenReturn(true);
            when(sudokuSolveService.solveBoard(testSudokuObject)).thenCallRealMethod();

            underTest.generateGame("");

            verify(sudokuObjectService, times(1)).getSudokuObject();
            verify(clearBoardService, times(1)).clearBoard(testSudokuObject);
            verify(boardValueManipulationService, times(1)).changeNullToZeroOnBoard(testSudokuObject);
            verify(tempSudokuObjectService, times(1)).setSudokuObject(copyObjectService.deepCopy(testSudokuObject));
        }
    }

    @Nested
    class GenerateNumbersInDiagonalBoxes {
        @Test
        void generateNumbersInDiagonalBoxes() {
            Integer[][] board = {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
            SudokuObject sudokuObject = new SudokuObject(board, 9, 3, 3);
            when(sudokuSolveService.isValidPlacement(any(), anyInt(), anyInt(), anyInt())).thenReturn(true);
            underTest.generateNumbersInDiagonalBoxes(sudokuObject);
            assertAll(
                    () -> assertThat(sudokuObject.getValueFromArray(0, 0)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(0, 0)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(0, 2)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(2, 2)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(0, 3)).isZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(2, 3)).isZero(),

                    () -> assertThat(sudokuObject.getValueFromArray(3, 3)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(3, 5)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(5, 5)).isLessThan(10).isNotZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(3, 2)).isZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(5, 6)).isZero(),

                    () -> assertThat(sudokuObject.getValueFromArray(6, 6)).isLessThan(10).isPositive(),
                    () -> assertThat(sudokuObject.getValueFromArray(6, 8)).isLessThan(10).isPositive(),
                    () -> assertThat(sudokuObject.getValueFromArray(8, 8)).isLessThan(10).isPositive(),
                    () -> assertThat(sudokuObject.getValueFromArray(3, 2)).isZero(),
                    () -> assertThat(sudokuObject.getValueFromArray(5, 6)).isZero()
            );
        }
    }

    @Nested
    class RandomCleanBoard {
        @Test
        void thisRemovesNumbersFromTheBoardAtRandomPlaces() {
            int testPercentToDelete = 65;
            Integer[][] board = {
                    {3, 1, 7, 2, 5, 4, 6, 8, 9},
                    {9, 2, 6, 1, 7, 8, 3, 4, 5},
                    {8, 4, 5, 3, 6, 9, 2, 1, 7},
                    {2, 7, 8, 5, 3, 1, 9, 6, 4},
                    {4, 3, 9, 6, 8, 7, 1, 5, 2},
                    {5, 6, 1, 9, 4, 2, 7, 3, 8},
                    {6, 8, 4, 7, 9, 3, 5, 2, 1},
                    {7, 5, 2, 8, 1, 6, 4, 9, 3},
                    {1, 9, 3, 4, 2, 5, 8, 7, 6}
            };
            SudokuObject testSudokuObject = new SudokuObject(board, 9, 3, 3);

            underTest.randomCleanBoard(testSudokuObject, testPercentToDelete);
            int counter = -1;
            for (int row = 0; row < testSudokuObject.getSudokuSize(); row++) {
                for (int column = 0; column < testSudokuObject.getSudokuSize(); column++) {
                    if (Objects.equals(testSudokuObject.getBoard()[row][column], 0)) {
                        counter++;
                    }
                }
            }

            assertThat((int) Math.pow(testSudokuObject.getSudokuSize(), 2) * testPercentToDelete / 100).isEqualTo(counter);
        }
    }
}