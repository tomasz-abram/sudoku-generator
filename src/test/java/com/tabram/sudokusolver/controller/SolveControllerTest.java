package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.service.SudokuSolveService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SolveController.class)
class SolveControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SudokuSolveService sudokuSolveService;

    @Nested
    class SolveAll {
        @Test
        void validInput_Return302() throws Exception {
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

            mockMvc.perform(put("/solve-all")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDtoTest))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(sudokuSolveService, times(1)).solveAll(sudokuObjectDtoTest);
        }

        @Test
        void invalidInput_NumberOutOfRange_Return302_AndErrorMsg() throws Exception {
            Integer[][] boardNull = {
                    {12, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(boardNull);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/solve-all")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(1))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(sudokuSolveService, never()).solveAll(any());
        }

        @Test
        void invalidInput_NumberOutOfRange_AndInvalidPlacement_Return302_AndErrorMsg() throws Exception {
            Integer[][] boardNull = {
                    {12, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, 2}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(boardNull);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/solve-all")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(2))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(sudokuSolveService, never()).solveAll(any());
        }

    }

    @Nested
    class SolveCell {

        @Test
        void validInput_Return302() throws Exception {
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
            String cellIdTest = "cell[1][2]";

            mockMvc.perform(put("/solve-cell")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDtoTest)
                            .param("solveCell", cellIdTest))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(sudokuSolveService, times(1)).solveCell(sudokuObjectDtoTest, cellIdTest);
        }

        @Test
        void invalidInput_NumberOutOfRange_Return302_AndErrorMsg() throws Exception {
            Integer[][] boardNull = {
                    {12, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(boardNull);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);
            String cellIdTest = "cell[1][2]";

            mockMvc.perform(put("/solve-cell")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto)
                            .param("solveCell", cellIdTest))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(1))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(sudokuSolveService, never()).solveCell(any(), anyString());
        }

        @Test
        void invalidInput_NumberOutOfRange_AndInvalidPlacement_Return302_AndErrorMsg() throws Exception {
            Integer[][] boardNull = {
                    {12, null, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, null, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, 2}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(boardNull);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);
            String cellIdTest = "cell[1][2]";

            mockMvc.perform(put("/solve-cell")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto)
                            .param("solveCell", cellIdTest))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(2))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(sudokuSolveService, never()).solveBoard(any());
        }

        @Test
        void invalidInput_cellNotSelected_Return302_AndErrorMsg() throws Exception {
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
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(boardNull);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/solve-cell")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto)
                            .param("solveCell", "notSelected"))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.flash().attributeCount(1))
                    .andDo(print());

            verify(sudokuSolveService, never()).solveCell(any(), anyString());
        }
    }
}