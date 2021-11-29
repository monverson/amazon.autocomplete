package com.amazon.autocomplete.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreCalculator {
    private String word;
    private Double matches;
}
