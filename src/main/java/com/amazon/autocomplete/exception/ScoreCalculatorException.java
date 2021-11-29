package com.amazon.autocomplete.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreCalculatorException extends RuntimeException{
    private String message;
}
