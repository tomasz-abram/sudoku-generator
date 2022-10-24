package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileIOServiceTest {
    @Mock
    private SudokuObjectRepository sudokuObjectRepository;

    @InjectMocks
    private FileIOService underTest;

    @Nested
    class ExportSudokuObject {
        @Test
        void exportBoard() {
            SudokuObject testSudoku = new SudokuObject(new Integer[4][4], 4, 2, 2);
            String response = "{\"board\":[[null,null,null,null],[null,null,null,null],[null,null,null,null],[null,null,null,null]],\"sudokuSize\":4,\"quantityBoxesHeight\":2,\"quantityBoxesWidth\":2}";
            String actualJson = underTest.exportSudokuObject(testSudoku);
            assertEquals(response, actualJson);
        }
    }

    @Nested
    class ImportSudokuObject {
        @Test
        void importBoard() {
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
            MockMultipartFile testFile = new MockMultipartFile("file", "sudoku.json", MediaType.APPLICATION_JSON_VALUE, "{\"board\":[[null,null,4,null,6,null,null,null,2],[3,null,null,5,null,null,null,null,7],[null,null,null,null,null,null,null,null,null],[1,null,null,null,8,null,null,null,null],[null,3,null,null,4,null,7,null,8],[5,null,null,null,7,null,null,null,6],[null,null,null,null,null,null,1,8,null],[2,null,null,9,null,null,null,null,3],[null,1,null,6,null,null,null,2,null]],\"sudokuSize\":9,\"quantityBoxesHeight\":3,\"quantityBoxesWidth\":3}".getBytes());

            underTest.importSudokuObject(testFile);

            ArgumentCaptor<SudokuObject> argumentCaptor = ArgumentCaptor.forClass(SudokuObject.class);
            verify(sudokuObjectRepository).setSudokuObject(argumentCaptor.capture());
            SudokuObject capturedSudoku = argumentCaptor.getValue();
            assertAll(
                    () -> assertEquals(testSudokuObject.getSudokuSize(), capturedSudoku.getSudokuSize()),
                    () -> assertEquals(testSudokuObject.getQuantityBoxesHeight(), capturedSudoku.getQuantityBoxesHeight()),
                    () -> assertEquals(testSudokuObject.getQuantityBoxesWidth(), capturedSudoku.getQuantityBoxesWidth()),
                    () -> assertThat(testSudokuObject.getBoard()).isDeepEqualTo(capturedSudoku.getBoard()));
        }
    }
}