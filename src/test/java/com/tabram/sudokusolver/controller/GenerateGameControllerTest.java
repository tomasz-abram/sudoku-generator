package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import com.tabram.sudokusolver.repository.TempSudokuObject;
import com.tabram.sudokusolver.service.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenerateGameController.class)
class GenerateGameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenerateSudokuGameService<SudokuObject> generateSudokuGameService;
    @MockBean
    private ClearBoardService clearBoardService;
    @MockBean
    private SudokuObjectRepository sudokuObjectRepository;
    @MockBean
    private BoardValueManipulation<SudokuObject> boardValueManipulation;
    @MockBean
    private SudokuSolveService<SudokuObjectAbstract> sudokuSolveService;
    @MockBean
    private CopyObjectService copyObjectService;
    @MockBean
    private TempSudokuObject tempSudokuObject;

    @Nested
    class GenerateGame {
        @ParameterizedTest
        @ValueSource(strings = {"Easy", "Medium", "Hard"})
        void generateGame_Return302_WithNewGame(String input) throws Exception {
            SudokuObject testSudokuObject = new SudokuObject(new Integer[9][9], 9, 3, 3);
            when(sudokuObjectRepository.getSudokuObject()).thenReturn(testSudokuObject);

            mockMvc.perform(get("/generate-game")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .param("level", input))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(sudokuObjectRepository, times(1)).getSudokuObject();
            verify(clearBoardService, times(1)).clearBoard(testSudokuObject);
            verify(boardValueManipulation, times(1)).changeNullToZeroOnBoard(testSudokuObject);
            verify(generateSudokuGameService, times(1)).generateNumbersInDiagonalBoxes(testSudokuObject);
            verify(sudokuSolveService, times(1)).solveBoard(testSudokuObject);
            verify(tempSudokuObject, times(1)).setSudokuObject(copyObjectService.deepCopy(testSudokuObject));
            verify(generateSudokuGameService, times(1)).randomCleanBoard(eq(testSudokuObject), anyInt());
        }


        @Test
        void whenTheOptionsNotSelected_Return302() throws Exception {
            mockMvc.perform(get("/generate-game")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .param("level", ""))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(sudokuObjectRepository, never()).getSudokuObject();
            verify(clearBoardService, never()).clearBoard(any());
            verify(boardValueManipulation, never()).changeNullToZeroOnBoard(any());
            verify(generateSudokuGameService, never()).generateNumbersInDiagonalBoxes(any());
            verify(sudokuSolveService, never()).solveBoard(any());
            verify(tempSudokuObject, never()).setSudokuObject(copyObjectService.deepCopy(any()));
            verify(generateSudokuGameService, never()).randomCleanBoard(any(SudokuObject.class), anyInt());
        }
    }
}