package com.tabram.sudokusolver.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabram.sudokusolver.dto.SudokuBoardObjectDto;
import com.tabram.sudokusolver.model.SudokuBoardObject;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public SudokuBoardObject mapperToSudokuBoardObject(SudokuBoardObjectDto objects){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(objects,SudokuBoardObject.class);
    }
}
