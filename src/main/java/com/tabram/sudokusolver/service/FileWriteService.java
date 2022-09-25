package com.tabram.sudokusolver.service;

import com.tabram.sudokusolver.model.SudokuBoardObject;
import com.tabram.sudokusolver.repository.SudokuBoardRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class FileWriteService {

    private final SudokuBoardRepository sudokuBoardRepository;

    public FileWriteService(SudokuBoardRepository sudokuBoardRepository) {
        this.sudokuBoardRepository = sudokuBoardRepository;
    }

    @SuppressWarnings("unchecked")
    public void writeFile() {
        SudokuBoardObject sudokuBoardObject = sudokuBoardRepository.getSudokuBoardObject();

        JSONArray boardJSON = new JSONArray();
        for (int row = 0; row < sudokuBoardObject.getSudokuSize(); row++) {
            JSONArray tempJSONArray = new JSONArray();
            for (int column = 0; column < sudokuBoardObject.getSudokuSize(); column++) {
                tempJSONArray.add(sudokuBoardObject.getValueFromArray(row, column));
            }
            boardJSON.add(tempJSONArray);
        }

        JSONObject sudokuDetailsJSON = new JSONObject();
        sudokuDetailsJSON.put("sudokuSize", sudokuBoardObject.getSudokuSize());
        sudokuDetailsJSON.put("quantityBoxesHeight", sudokuBoardObject.getQuantityBoxesHeight());
        sudokuDetailsJSON.put("quantityBoxesWidth", sudokuBoardObject.getQuantityBoxesWidth());
        sudokuDetailsJSON.put("board", boardJSON);

        JSONObject sudokuObjectJSON = new JSONObject();
        sudokuObjectJSON.put("sudokuBoardObject", sudokuDetailsJSON);

        try (FileWriter file = new FileWriter("C:\\Users\\tabra\\OneDrive\\Pulpit\\testFileSudoku.txt")) {
            file.write(sudokuObjectJSON.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
