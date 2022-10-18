package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.dto.SudokuObjectDto;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.service.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SudokuObjectService sudokuObjectService;
    @MockBean
    private ClearBoardService clearBoardService;
    @MockBean
    private BoardValueManipulationService<SudokuObjectAbstract> boardValueManipulationService;
    @MockBean
    private BoardSizeService boardSizeService;
    @MockBean
    private MapperService mapperService;


    @Nested
    class Home {
        @Test
        void homePage() throws Exception {
            Integer[][] board = {
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
            SudokuObject testSudokuObject = new SudokuObject(board, 9, 3, 3);
            when(boardValueManipulationService.changeZeroToNullOnBoard(sudokuObjectService.getSudokuObject())).thenReturn(testSudokuObject);

            MvcResult mvcResult = mockMvc.perform(get("/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            ModelAndView modelAndView = mvcResult.getModelAndView();
            assertThat(modelAndView).isNotNull();
            assertEquals(testSudokuObject, modelAndView.getModel().get("sudokuObject"));
            assertThat(modelAndView.getModel().get("fileBucket")).isNotNull();
        }
    }

    @Nested
    class Save {
        @Test
        void validInput_Return302() throws Exception {
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();

            mockMvc.perform(put("/save")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andDo(print())
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection());

            verify(mapperService, times(1)).mapperToSudokuBoardObject(sudokuObjectDto);
            verify(sudokuObjectService, times(1)).setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        }

        @Test
        void invalidInput_NumberOutRange_Return302_AndErrorMsg() throws Exception {
            Integer[][] board = {
                    {null, 12, 4, null, 6, null, null, null, 2},
                    {3, null, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, 12, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(board);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/save")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(1))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(boardValueManipulationService, never()).changeNullToZeroOnBoard(any());
            verify(mapperService, never()).mapperToSudokuBoardObject(any());
            verify(sudokuObjectService, never()).setSudokuObject(any());
        }

        @Test
        void invalidInput_NumberOutRangeAndInvalidPlacement_Return302_AndErrorMsg() throws Exception {
            Integer[][] board = {
                    {null, 12, 4, 4, 6, null, null, null, 2},
                    {3, 4, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, 12, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(board);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/save")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(2))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(boardValueManipulationService, never()).changeNullToZeroOnBoard(any());
            verify(mapperService, never()).mapperToSudokuBoardObject(any());
            verify(sudokuObjectService, never()).setSudokuObject(any());
        }
    }

    @Nested
    class ClearBoard {
        @Test
        void clearBoard_Return302() throws Exception {
            mockMvc.perform(delete("/clear")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8"))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(clearBoardService, times(1)).clearBoard(any());
            verify(sudokuObjectService, times(1)).getSudokuObject();
        }
    }

    @Nested
    class ChangeBoardSize {
        @Test
        void changeBoardSize_Return302() throws Exception {

            mockMvc.perform(put("/change-board-size")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .param("changeBoardSize", "25"))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(boardSizeService, times(1)).generateNewBoard(25);
            verify(sudokuObjectService, times(1)).setSudokuObject(any());
        }
    }

    @Nested
    class Check {

        @Test
        void validInput_Return302() throws Exception {
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();

            mockMvc.perform(put("/check")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andDo(print())
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection());

            verify(mapperService, times(1)).mapperToSudokuBoardObject(sudokuObjectDto);
            verify(sudokuObjectService, times(1)).setSudokuObject(mapperService.mapperToSudokuBoardObject(sudokuObjectDto));
        }

        @Test
        void invalidInput_InvalidNumberPlacement_Return302_AndErrorMsg() throws Exception {
            Integer[][] board = {
                    {4, 4, 4, null, 6, null, null, null, 2},
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
            sudokuObjectDto.setBoard(board);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/check")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(1))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(boardValueManipulationService, never()).changeNullToZeroOnBoard(any());
            verify(mapperService, never()).mapperToSudokuBoardObject(any());
            verify(sudokuObjectService, never()).setSudokuObject(any());
        }

        @Test
        void invalidInput_InvalidPlacementAndNumberOutRange_Return302_AndErrorMsg() throws Exception {
            Integer[][] board = {
                    {4, 12, 4, 4, 6, null, null, null, 2},
                    {3, 4, null, 5, null, null, null, null, 7},
                    {null, null, null, null, null, null, null, null, null},
                    {1, null, null, null, 8, null, 12, null, null},
                    {null, 3, null, null, 4, null, 7, null, 8},
                    {5, null, null, null, 7, null, null, null, 6},
                    {null, null, null, null, null, null, 1, 8, null},
                    {2, null, null, 9, null, null, null, null, 3},
                    {null, 1, null, 6, null, null, null, 2, null}
            };
            SudokuObjectDto sudokuObjectDto = new SudokuObjectDto();
            sudokuObjectDto.setBoard(board);
            sudokuObjectDto.setSudokuSize(9);
            sudokuObjectDto.setQuantityBoxesHeight(3);
            sudokuObjectDto.setQuantityBoxesWidth(3);

            mockMvc.perform(put("/check")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .flashAttr("sudokuObject", sudokuObjectDto))
                    .andExpect(model().attributeHasErrors("sudokuObject"))
                    .andExpect(model().errorCount(2))
                    .andExpect(status().isOk())
                    .andDo(print());

            verify(boardValueManipulationService, never()).changeNullToZeroOnBoard(any());
            verify(mapperService, never()).mapperToSudokuBoardObject(any());
            verify(sudokuObjectService, never()).setSudokuObject(any());
        }
    }
}