package com.tabram.sudokusolver.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tabram.sudokusolver.model.SudokuBoardObject;
import org.springframework.stereotype.Service;

@Service
public class JsonExporterService {
    public String export(SudokuBoardObject sudokuBoardObject) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(sudokuBoardObject);
    }

}
