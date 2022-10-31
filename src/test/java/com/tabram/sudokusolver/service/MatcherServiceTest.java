package com.tabram.sudokusolver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MatcherServiceTest {

    @InjectMocks
    private MatcherService underTest;

    @Test
    void cellIdMatcher() {
        String cellIdTest = "cell[1][2]";

        List<String> actualList = underTest.cellIdMatcher(cellIdTest);

        assertEquals("1", actualList.get(0));
        assertEquals("2", actualList.get(1));
    }
}