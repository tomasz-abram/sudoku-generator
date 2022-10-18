package com.tabram.sudokusolver.validation;

import com.tabram.sudokusolver.model.SudokuObject;
import com.tabram.sudokusolver.model.SudokuObjectAbstract;
import com.tabram.sudokusolver.repository.TempSudokuObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CompareWithTempSudokuBoardValidation {

    private final TempSudokuObjectRepository tempSudokuObjectRepository;

    @Autowired
    public CompareWithTempSudokuBoardValidation(TempSudokuObjectRepository tempSudokuObjectRepository) {
        this.tempSudokuObjectRepository = tempSudokuObjectRepository;
    }

    public <T extends SudokuObjectAbstract> boolean compare(T inSudokuObject) {
        SudokuObject tempBoardObject = tempSudokuObjectRepository.getSudokuObject();
        int zeroOnBoard = 0;

        if (tempBoardObject == null ||
                tempBoardObject.getSudokuSize() != inSudokuObject.getSudokuSize() ||
                tempBoardObject.getQuantityBoxesHeight() != inSudokuObject.getQuantityBoxesHeight() ||
                tempBoardObject.getQuantityBoxesWidth() != inSudokuObject.getQuantityBoxesWidth()) {
            return false;
        }

        for (int row = 0; row < inSudokuObject.getSudokuSize(); row++) {
            for (int column = 0; column < inSudokuObject.getSudokuSize(); column++) {
                if (inSudokuObject.getValueFromArray(row, column) != 0) {
                    if (!Objects.equals(inSudokuObject.getValueFromArray(row, column), tempBoardObject.getValueFromArray(row, column))) {
                        return false;
                    }
                } else {
                    zeroOnBoard++;
                }
            }
        }

        return zeroOnBoard != inSudokuObject.getSudokuSize() * inSudokuObject.getSudokuSize();
    }
}
