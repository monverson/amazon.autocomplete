package com.amazon.autocomplete.controller;

import com.amazon.autocomplete.entity.ScoreCalculator;
import com.amazon.autocomplete.exception.ScoreCalculatorException;
import com.amazon.autocomplete.service.ScoreCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class ScoreCalculatorController {

    private final ScoreCalculatorService scoreCalculatorService;

    public ScoreCalculatorController(ScoreCalculatorService scoreCalculatorService) {
        this.scoreCalculatorService = scoreCalculatorService;
    }

    @RequestMapping("/estimate")
    public ResponseEntity<ScoreCalculator> estimate(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ScoreCalculatorException("Please provide keyword parameter");
        }
        Double finalScore = scoreCalculatorService.getFinalScore(keyword);
        Map<String, Double> finalScoreForEachPrefix = scoreCalculatorService.getFinalScoreForEachPrefix(keyword);
        scoreInformationForEachPrefix(finalScoreForEachPrefix);
        ScoreCalculator scoreCalculator = new ScoreCalculator(keyword, finalScore);
        return new ResponseEntity<>(scoreCalculator, HttpStatus.OK);
    }
    /**
     *This method prints the scores of each prefix of the search results entered by the user one by one.
     *
     * @param map contains each matches words with their score
     */
    private void scoreInformationForEachPrefix(Map<String, Double> map) {
        map.forEach((word, score) -> log.info("Keyword '{}' score '{}'", word, score));
    }
}
