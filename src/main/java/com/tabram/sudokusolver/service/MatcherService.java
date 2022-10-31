package com.tabram.sudokusolver.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MatcherService {

    public List<String> cellIdMatcher(String cellId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(cellId);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }
}
