package com.tabram.sudokugenerator.controller;

import com.tabram.sudokugenerator.model.SudokuObject;
import com.tabram.sudokugenerator.service.FileIOService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileIOController.class)
class FileIOControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private FileIOService fileIOService;

    @Nested
    class DownloadFile {
        @Test
        void downloadFile_ThenReturnJsonFile() throws Exception {
            SudokuObject sudokuObjectTest = new SudokuObject(new Integer[9][9], 9, 3, 3);
            String sudokuJsonStringTest = "{\"board\":[[null,null,4,null,6,null,null,null,2],[3,null,null,5,null,null,null,null,7],[null,null,null,null,null,null,null,null,null],[1,null,null,null,8,null,null,null,null],[null,3,null,null,4,null,7,null,8],[5,null,null,null,7,null,null,null,6],[null,null,null,null,null,null,1,8,null],[2,null,null,9,null,null,null,null,3],[null,1,null,6,null,null,null,2,null]],\"sudokuSize\":9,\"quantityBoxesHeight\":3,\"quantityBoxesWidth\":3}";
            when(fileIOService.exportSudokuObject(sudokuObjectTest)).thenReturn(sudokuJsonStringTest);
            when(fileIOService.downloadFile()).thenReturn(sudokuJsonStringTest.getBytes());

            MvcResult mvcResult = mockMvc.perform(get("/download-file")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful())
                    .andDo(print())
                    .andReturn();

            assertThat(mvcResult.getResponse()).isNotNull();
            assertEquals(sudokuJsonStringTest, mvcResult.getResponse().getContentAsString());
            assertEquals(MediaType.APPLICATION_JSON_VALUE, mvcResult.getResponse().getContentType());
            verify(fileIOService, times(2)).downloadFile();
        }
    }

    @Nested
    class UploadFile {
        @Test
        void uploadFileIsValid_ThenReturn302_WithSuccessMsg() throws Exception {
            MockMultipartFile testFile = new MockMultipartFile("file", "sudoku.json", MediaType.APPLICATION_JSON_VALUE, "{\"board\":[[null,null,4,null,6,null,null,null,2],[3,null,null,5,null,null,null,null,7],[null,null,null,null,null,null,null,null,null],[1,null,null,null,8,null,null,null,null],[null,3,null,null,4,null,7,null,8],[5,null,null,null,7,null,null,null,6],[null,null,null,null,null,null,1,8,null],[2,null,null,9,null,null,null,null,3],[null,1,null,6,null,null,null,2,null]],\"sudokuSize\":9,\"quantityBoxesHeight\":3,\"quantityBoxesWidth\":3}".getBytes());

            MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            mockMvc.perform(multipart("/upload-file")
                            .file(testFile)
                            .contentType(MediaType.TEXT_HTML))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"))
                    .andExpect(MockMvcResultMatchers.flash().attributeCount(1))
                    .andDo(print());

            verify(fileIOService, times(1)).importSudokuObject(testFile);
        }

        @Test
        void uploadedFileIsInvalid_ThenReturn302_WithErrorMsg() throws Exception {
            MockMultipartFile testFile = new MockMultipartFile("file", "sudoku.json", MediaType.APPLICATION_JSON_VALUE, "{\"board\":[[null,null,4,null,6,null,null,null,2],[3,null,null,5,null,null,null,null,7],[null,null,null,null,null,null,null,null,null],[1,null,null,null,8,null,null,null,null],[null,3,null,null,4,null,7,null,8],[5,null,null,null,7,null,null,null,6],[null,null,null,null,null,null,1,8,null],[2,null,null,9,null,null,null,null,3],[null,1,null,6,null,null,null,2,null]],\"sudokuSize\":9,\"quantityBoxesHeight\":2,\"quantityBoxesWidth\":3}".getBytes());

            MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            mockMvc.perform(multipart("/upload-file")
                            .file(testFile)
                            .contentType(MediaType.APPLICATION_JSON))

                    .andExpect(MockMvcResultMatchers.flash().attributeCount(1))
                    .andExpect(MockMvcResultMatchers.flash().attributeExists("errors"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"))
                    .andDo(print());
        }
    }
}