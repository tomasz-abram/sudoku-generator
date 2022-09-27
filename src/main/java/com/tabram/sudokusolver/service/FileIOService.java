package com.tabram.sudokusolver.service;

import com.google.gson.Gson;
import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class FileIOService {
    private final SudokuBoardRepository sudokuBoardRepository;

    public FileIOService(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    public String exportBoard(SudokuBoardObject sudokuBoardObject) {
        String jsonBoard = null;
        try {
            Gson gson = new Gson();
            jsonBoard = gson.toJson(sudokuBoardObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return jsonBoard;
    }

    public void importBoard(MultipartFile file) {
        SudokuBoardObject sudokuBoardObject = new SudokuBoardObject();

        try {
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(file.getInputStream());
            sudokuBoardObject = gson.fromJson(reader, SudokuBoardObject.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        sudokuBoardRepository.setSudokuBoardObject(sudokuBoardObject);
    }

}
