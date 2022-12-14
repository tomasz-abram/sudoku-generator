package com.tabram.sudokugenerator.service;

import com.google.gson.Gson;
import com.tabram.sudokugenerator.model.SudokuObject;
import org.springframework.stereotype.Service;

@Service
public class CopyObjectService {

    public SudokuObject deepCopy(SudokuObject object){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object),SudokuObject.class);
    }
}
