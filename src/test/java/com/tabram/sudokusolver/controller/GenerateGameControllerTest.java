package com.tabram.sudokusolver.controller;

import com.tabram.sudokusolver.service.GenerateSudokuGameService;
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
    private GenerateSudokuGameService generateSudokuGameService;

    @Nested
    class GenerateGame {
        @ParameterizedTest
        @ValueSource(strings = {"Easy", "Medium", "Hard"})
        void generateGame_Return302_WithNewGame(String input) throws Exception {

            mockMvc.perform(get("/generate-game")
                            .contentType(MediaType.TEXT_HTML)
                            .characterEncoding("UTF-8")
                            .param("level", input))
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());

            verify(generateSudokuGameService, times(1)).generateGame(input);

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

            verify(generateSudokuGameService, never()).generateGame(any());

        }
    }
}