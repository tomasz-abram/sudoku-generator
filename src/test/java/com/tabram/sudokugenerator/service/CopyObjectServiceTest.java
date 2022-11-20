package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.model.SudokuObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CopyObjectServiceTest {

    private CopyObjectService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CopyObjectService();
    }

    @Nested
    class DeepCopy {
        @Test
        void thisMakesCopiesOfTheObject() {
            // given
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
            // when
            SudokuObject sudokuActual = underTest.deepCopy(testSudokuObject);
            // then
            assertAll(
                    () -> assertEquals(testSudokuObject.getSudokuSize(), sudokuActual.getSudokuSize()),
                    () -> assertEquals(testSudokuObject.getQuantityBoxesHeight(), sudokuActual.getQuantityBoxesHeight()),
                    () -> assertEquals(testSudokuObject.getQuantityBoxesWidth(), sudokuActual.getQuantityBoxesWidth()),
                    () -> assertThat(testSudokuObject.getBoard()).isDeepEqualTo(sudokuActual.getBoard()));
        }
    }
}