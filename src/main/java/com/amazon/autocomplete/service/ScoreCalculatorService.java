package com.amazon.autocomplete.service;


import java.util.Map;

public interface ScoreCalculatorService {

    Double getFinalScore(String keyword);

    Map<String,Double> getFinalScoreForEachPrefix(String keyword);
}
