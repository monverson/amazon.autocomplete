package com.amazon.autocomplete.service.impl;

import com.amazon.autocomplete.client.AutoCompleteResult;
import com.amazon.autocomplete.service.ScoreCalculatorService;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class ScoreCalculatorCalculatorServiceImpl implements ScoreCalculatorService {

    private final AutoCompleteResult autoCompleteResult;

    public ScoreCalculatorCalculatorServiceImpl(AutoCompleteResult autoCompleteResult) {
        this.autoCompleteResult = autoCompleteResult;
    }

    /**
     * This method return final score of user search keyword.
     * For example : user entered iphone then :
     * iphone-> 100
     *
     * @param keyword user search text
     */
    @Override
    public Double getFinalScore(String keyword) {
        List<String> suggestionList = autoCompleteResult.getAmazonAutoCompleteResult(keyword);
        if (suggestionList.isEmpty()) {
            return 0.0;
        }
        Integer matches = getMatchingNumber(suggestionList, keyword);
        return calculateScore(keyword, matches);
    }

    /**
     * This method return final score of each prefix entered by user.
     * For example : user entered iphone then : return each prefix score
     * i-> 0
     * ip-> 0
     * iph-> 15
     * ipho-> 20
     * iphon-> 25
     * iphone-> 100
     *
     * @param keyword user search text
     */
    @Override
    public Map<String, Double> getFinalScoreForEachPrefix(String keyword) {
        List<String> suggestionList = autoCompleteResult.getAmazonAutoCompleteResult(keyword);
        if (suggestionList.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Double> scoreOfEachCharacterInKeyword = new LinkedHashMap<>();
        IntStream.range(0, keyword.length()).mapToObj(i -> keyword.substring(0, i + 1)).forEach(keywordPrefix -> {
            Integer match = getMatchingNumber(suggestionList, keywordPrefix);
            Double finalScore = calculateScore(keywordPrefix, match);
            scoreOfEachCharacterInKeyword.put(keywordPrefix, finalScore);
        });
        return scoreOfEachCharacterInKeyword;
    }

    public Double calculateScore(String keyword, Integer matches) {
        val keywordWords = keyword.split(" ");
        return (double) (matches * 100 / keywordWords.length);
    }

    private Integer getMatchingNumber(List<String> suggestionList, String keyword) {
        Integer maxMatchingNumber = 0;
        for (String suggestion : suggestionList) {
            maxMatchingNumber = getMaxMatchingNumber(keyword, maxMatchingNumber, suggestion);
        }
        return maxMatchingNumber;
    }

    /**
     * This method return the maximum matches result. For example, suggestion list has
     * 10 different string and user keyword 3 separate string. After compare one-by-one some
     * string match 2 some 3. This method returns maximum matches result.
     *
     * @param keyword           user search text
     * @param maxMatchingNumber maximum matching number
     * @param suggestion        amazon autocomplete resultList
     */
    private Integer getMaxMatchingNumber(String keyword, Integer maxMatchingNumber, String suggestion) {
        Integer numberOfMatchingWord = countKeywordMatchInSuggestionList(suggestion, keyword);
        if (numberOfMatchingWord > maxMatchingNumber) {
            maxMatchingNumber = numberOfMatchingWord;
        }
        return maxMatchingNumber;
    }

    /**
     * This method compare words entered my user and ten amazon-auto complete results.
     * and return the number of matches results
     *
     * @param suggestion amazon autocomplete resultList
     * @param keyword    user search text
     */
    private static Integer countKeywordMatchInSuggestionList(String suggestion, String keyword) {

        String[] keywordList = keyword.split(" ");
        String[] suggestionList = suggestion.split(" ");

        int match = 0;
        int maximumMatch = 0;

        for (String suggestionWord : suggestionList) {
            if (suggestionWord.equals(keywordList[match])) {
                match++;
                maximumMatch = match;
                if (match == keywordList.length) {
                    break;
                }
            } else {
                match = 0;
            }
        }
        return maximumMatch;
    }


}
