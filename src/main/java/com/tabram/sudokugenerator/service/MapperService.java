package com.tabram.sudokugenerator.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabram.sudokugenerator.dto.SudokuObjectDto;
import com.tabram.sudokugenerator.model.SudokuObject;
import org.springframework.stereotype.Service;

@Service
public class MapperService {

    public SudokuObject mapperToSudokuBoardObject(SudokuObjectDto objects){
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.convertValue(objects, SudokuObject.class);
    }
}
