package com.tabram.sudokusolver.service;

import com.google.gson.Gson;
import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.repository.SudokuObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class FileIOService {
    private final SudokuObjectRepository sudokuObjectRepository;
    private final SudokuObjectService sudokuObjectService;

    public FileIOService(SudokuObjectRepository sudokuObjectRepository, SudokuObjectService sudokuObjectService) {
        this.sudokuObjectRepository = sudokuObjectRepository;
        this.sudokuObjectService = sudokuObjectService;
    }

    public byte[] downloadFile() {
        SudokuObject sudokuObject = sudokuObjectService.getSudokuObject();
        String sudokuJsonString = exportSudokuObject(sudokuObject);
        return sudokuJsonString.getBytes();
    }


    public String exportSudokuObject(SudokuObject sudokuObject) {
        String jsonBoard = null;
        try {
            Gson gson = new Gson();
            jsonBoard = gson.toJson(sudokuObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonBoard;
    }

    public void importSudokuObject(MultipartFile file) {
        SudokuObject sudokuObject = new SudokuObject();

        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(file.getInputStream());
            sudokuObject = gson.fromJson(reader, SudokuObject.class);
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sudokuObjectRepository.setSudokuObject(sudokuObject);
    }

}
