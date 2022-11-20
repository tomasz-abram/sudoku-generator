package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.dto.SudokuObjectDto;
import com.tabram.sudokugenerator.model.SudokuObject;
import com.tabram.sudokugenerator.validation.CompareWithTempSudokuBoardValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SudokuSolveServiceTest {

    private SudokuSolveService underTest;
    @Mock
    private SudokuObjectService sudokuObjectService;
    @Spy
    private BoardValueManipulationService<SudokuObjectDto> boardValueManipulationService;
    @Mock
    private MapperService mapperService;
    @Mock
    private TempSudokuObjectService tempSudokuObjectService;
    @Mock
    private CompareWithTempSudokuBoardValidation compareWithTempSudokuBoardValidation;
    @Mock
    private MatcherService matcherService;

    private SudokuObject testSudokuObject;


    @BeforeEach
    void setUp() {
        underTest = new SudokuSolveService(sudokuObjectService, boardValueManipulationService, mapperService, tempSudokuObjectService, compareWithTempSudokuBoardValidation, matcherService);

        Integer[][] board = {
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
        testSudokuObject = new SudokuObject(board, 9, 3, 3);

    }

    @Nested
    class SolveAll {
        @Test
        void whenTheSolvedTableIsInTempSudokuItTakesTheSolutionAndClearsTempSudokuObject() {
            SudokuObjectDto sudokuObjectDtoTest = new SudokuObjectDto();
            Integer[][] boardNull = {
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
            sudokuObjectDtoTest.setBoard(boardNull);
            sudokuObjectDtoTest.setSudokuSize(9);
            sudokuObjectDtoTest.setQuantityBoxesWidth(3);
            sudokuObjectDtoTest.setQuantityBoxesHeight(3);
            Integer[][] boardTest = {
                    {9, 7, 4, 8, 6, 1, 3, 5, 2},
                    {3, 6, 1, 5, 9, 2, 8, 4, 7},
                    {8, 5, 2, 4, 3, 7, 9, 6, 1},
                    {1, 2, 7, 3, 8, 6, 5, 9, 4},
                    {6, 3, 9, 2, 4, 5, 7, 1, 8},
                    {5, 4, 8, 1, 7, 9, 2, 3, 6},
                    {4, 9, 6, 7, 2, 3, 1, 8, 5},
                    {2, 8, 5, 9, 1, 4, 6, 7, 3},
                    {7, 1, 3, 6, 5, 8, 4, 2, 9}
            };
            SudokuObject sudokuObjectTest = new SudokuObject(boardTest, 9, 3, 3);
            when(tempSudokuObjectService.getSudokuObject()).thenReturn(sudokuObjectTest);
            when(compareWithTempSudokuBoardValidation.compare(sudokuObjectDtoTest)).thenReturn(true);

            underTest.solveAll(sudokuObjectDtoTest);

            verify(boardValueManipulationService, times(1)).changeNullToZeroOnBoard(sudokuObjectDtoTest);
            verify(sudokuObjectService, times(1)).setSudokuObject(sudokuObjectTest);
            verify(tempSudokuObjectService, times(1)).setSudokuObject(null);
            verify(sudokuObjectService, never()).setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDtoTest));
        }

        @Test
        void whenTheSolutionDoesNotExistInTempSudokuThenItSolvesTheSudokuAndPutItInSudokuObjectRepository() {
            SudokuObjectDto sudokuObjectDtoTest = new SudokuObjectDto();
            Integer[][] boardZero = {
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
            sudokuObjectDtoTest.setBoard(boardZero);
            sudokuObjectDtoTest.setSudokuSize(9);
            sudokuObjectDtoTest.setQuantityBoxesWidth(3);
            sudokuObjectDtoTest.setQuantityBoxesHeight(3);

            underTest.solveAll(sudokuObjectDtoTest);

            verify(boardValueManipulationService, times(1)).changeNullToZeroOnBoard(sudokuObjectDtoTest);
            verify(tempSudokuObjectService, never()).getSudokuObject();
            verify(tempSudokuObjectService, never()).setSudokuObject(null);
            verify(sudokuObjectService, times(1)).setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDtoTest));
        }
    }

    @Nested
    class solveCell {

        @Test
        void thisShouldTakeCellFromTheSolvedSudokuFromTempSudoku() {
            SudokuObjectDto sudokuObjectDtoTest = new SudokuObjectDto();
            Integer[][] boardNull = {
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
            sudokuObjectDtoTest.setBoard(boardNull);
            sudokuObjectDtoTest.setSudokuSize(9);
            sudokuObjectDtoTest.setQuantityBoxesWidth(3);
            sudokuObjectDtoTest.setQuantityBoxesHeight(3);
            // Sudoku from repository
            SudokuObject sudokuObjectRepoTest = new SudokuObject(boardNull, 9, 3, 3);
            //Sudoku from TempSudokuObject
            Integer[][] boardZero = {
                    {9, 7, 4, 8, 6, 1, 3, 5, 2},
                    {3, 6, 1, 5, 9, 2, 8, 4, 7},
                    {8, 5, 2, 4, 3, 7, 9, 6, 1},
                    {1, 2, 7, 3, 8, 6, 5, 9, 4},
                    {6, 3, 9, 2, 4, 5, 7, 1, 8},
                    {5, 4, 8, 1, 7, 9, 2, 3, 6},
                    {4, 9, 6, 7, 2, 3, 1, 8, 5},
                    {2, 8, 5, 9, 1, 4, 6, 7, 3},
                    {7, 1, 3, 6, 5, 8, 4, 2, 9}
            };
            SudokuObject sudokuObjectTempTest = new SudokuObject(boardZero, 9, 3, 3);
            String cellIdTest = "cell[1][2]";
            List<String> cellIdStringTest = new ArrayList<>();
            cellIdStringTest.add("1");
            cellIdStringTest.add("2");
            when(compareWithTempSudokuBoardValidation.compare(sudokuObjectDtoTest)).thenReturn(true);
            when(tempSudokuObjectService.getSudokuObject()).thenReturn(sudokuObjectTempTest);
            when(sudokuObjectService.getSudokuObject()).thenReturn(sudokuObjectRepoTest);
            when(matcherService.cellIdMatcher(cellIdTest)).thenReturn(cellIdStringTest);

            underTest.solveCell(sudokuObjectDtoTest, cellIdTest);

            verify(tempSudokuObjectService, times(1)).getSudokuObject();
            verify(sudokuObjectService, times(1)).getSudokuObject();
            verify(mapperService, never()).mapperToSudokuBoardObject(sudokuObjectDtoTest);
            verify(tempSudokuObjectService, never()).setSudokuObject(sudokuObjectTempTest);
        }

        @Test
        void thatShouldSolveTheWholeSudokuAddAnObjectToTempSudokuAndTakeCell() {
            SudokuObjectDto sudokuObjectDtoTest = (new SudokuObjectDto());
            Integer[][] boardNull = {
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
            sudokuObjectDtoTest.setBoard(boardNull);
            sudokuObjectDtoTest.setSudokuSize(9);
            sudokuObjectDtoTest.setQuantityBoxesWidth(3);
            sudokuObjectDtoTest.setQuantityBoxesHeight(3);
            SudokuObject sudokuObject = new SudokuObject(boardNull, 9, 3, 3);
            Integer[][] boardSolved = {
                    {9, 7, 4, 8, 6, 1, 3, 5, 2},
                    {3, 6, 1, 5, 9, 2, 8, 4, 7},
                    {8, 5, 2, 4, 3, 7, 9, 6, 1},
                    {1, 2, 7, 3, 8, 6, 5, 9, 4},
                    {6, 3, 9, 2, 4, 5, 7, 1, 8},
                    {5, 4, 8, 1, 7, 9, 2, 3, 6},
                    {4, 9, 6, 7, 2, 3, 1, 8, 5},
                    {2, 8, 5, 9, 1, 4, 6, 7, 3},
                    {7, 1, 3, 6, 5, 8, 4, 2, 9}
            };
            SudokuObject tempSudokuObject = new SudokuObject(boardSolved, 9, 3, 3);
            String cellIdTest = "cell[1][2]";
            List<String> cellIdStringTest = new ArrayList<>();
            cellIdStringTest.add("1");
            cellIdStringTest.add("2");
            when(compareWithTempSudokuBoardValidation.compare(sudokuObjectDtoTest)).thenReturn(false);
            when(matcherService.cellIdMatcher(cellIdTest)).thenReturn(cellIdStringTest);
            when(tempSudokuObjectService.getSudokuObject()).thenReturn(tempSudokuObject);
            when(sudokuObjectService.getSudokuObject()).thenReturn(sudokuObject);

            underTest.solveCell(sudokuObjectDtoTest, cellIdTest);

            verify(boardValueManipulationService, times(1)).changeNullToZeroOnBoard(sudokuObjectDtoTest);
            verify(mapperService, times(1)).mapperToSudokuBoardObject(sudokuObjectDtoTest);
            verify(tempSudokuObjectService, times(1)).setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDtoTest));
            verify(tempSudokuObjectService, times(1)).getSudokuObject();
            verify(sudokuObjectService, times(1)).getSudokuObject();
        }
    }

    @Nested
    class IsNumberInBox {
        @Test
        void theGivenNumberExistInTheBox() {
            Integer numberToCheck = 4;
            int row = 1;
            int column = 1;
            boolean actualNumberIsValid = underTest.isNumberInBox(testSudokuObject, numberToCheck, row, column);
            assertThat(actualNumberIsValid).isTrue();
        }

        @Test
        void theGivenNumberDoesNotExistInTheBox() {
            Integer numberToCheck = 5;
            int row = 1;
            int column = 1;
            boolean actualNumberIsValid = underTest.isNumberInBox(testSudokuObject, numberToCheck, row, column);
            assertThat(actualNumberIsValid).isFalse();
        }
    }

    @Nested
    class IsValidPlacement {
        @Test
        void theGivenNumberIsInCorrectPlace() {
            Integer numberToCheck = 2;
            int row = 1;
            int column = 1;
            boolean actualNumberIsValid = underTest.isValidPlacement(testSudokuObject, numberToCheck, row, column);
            assertThat(actualNumberIsValid).isTrue();
        }

        @Test
        void theGivenNumberIsNotInTheCorrectPlace() {
            Integer numberToCheck = 5;
            int row = 1;
            int column = 1;
            boolean actualNumberIsValid = underTest.isValidPlacement(testSudokuObject, numberToCheck, row, column);
            assertThat(actualNumberIsValid).isFalse();
        }
    }

    @Nested
    class SolveBoard {
        @Test
        void thisSolveTheSudokuTable() {
            underTest.solveBoard(testSudokuObject);
            boolean duplicates = false;
            Integer[] testArray = testSudokuObject.getBoard()[0];

            for (int i = 0; i < testSudokuObject.getSudokuSize(); i++) {
                for (int j = i + 1; j < testSudokuObject.getSudokuSize(); j++) {
                    if (j != i && testArray[j].equals(testArray[i])) {
                        duplicates = true;
                        break;
                    }
                }
            }

            assertThat(testSudokuObject.getValueFromArray(0, 0)).isNotZero().isLessThan(10);
            assertThat(testSudokuObject.getValueFromArray(8, 8)).isNotZero().isLessThan(10);
            assertThat(duplicates).isFalse();
        }

    }
}