package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SudokuSolveServiceTest {

    private SudokuSolveService<SudokuObject> underTest;
    private SudokuObject testSudokuObject;

    @BeforeEach
    void setUp() {
        underTest = new SudokuSolveService<>();

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