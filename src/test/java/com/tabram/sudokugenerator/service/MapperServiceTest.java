package com.tabram.sudokugenerator.service;

import com.tabram.sudokugenerator.dto.SudokuObjectDto;
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
class MapperServiceTest {

    private MapperService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MapperService();
    }

    @Nested
    class MapperTOSudokuBoardObject {
        @Test
        void mapperToSudokuBoardObject() {
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
            SudokuObjectDto testSudokuObjectDto = new SudokuObjectDto();
            testSudokuObjectDto.setBoard(board);
            testSudokuObjectDto.setSudokuSize(9);
            testSudokuObjectDto.setQuantityBoxesHeight(3);
            testSudokuObjectDto.setQuantityBoxesWidth(3);

            SudokuObject actualSudoku = underTest.mapperToSudokuBoardObject(testSudokuObjectDto);

            assertAll(
                    () -> assertEquals(testSudokuObjectDto.getSudokuSize(), actualSudoku.getSudokuSize()),
                    () -> assertEquals(testSudokuObjectDto.getQuantityBoxesHeight(), actualSudoku.getQuantityBoxesHeight()),
                    () -> assertEquals(testSudokuObjectDto.getQuantityBoxesWidth(), actualSudoku.getQuantityBoxesWidth()),
                    () -> assertThat(testSudokuObjectDto.getBoard()).isDeepEqualTo(actualSudoku.getBoard()));
        }
    }
}