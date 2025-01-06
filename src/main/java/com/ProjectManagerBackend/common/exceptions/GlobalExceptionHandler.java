package com.ProjectManagerBackend.common.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) ex.getCause();

            String invalidValue = invalidFormatException.getValue().toString();
            String fieldName = invalidFormatException.getPath().get(0).getFieldName();
            String errorMessage = getString(invalidFormatException, invalidValue, fieldName);

            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Handle default case
        return new ResponseEntity<>("Invalid request body", HttpStatus.BAD_REQUEST);
    }

    private static String getString(InvalidFormatException invalidFormatException, String invalidValue, String fieldName) {
        Class<?> fieldType = invalidFormatException.getTargetType();

        String errorMessage;
        if (fieldType.isEnum()) {
            // Get the enum values
            String validValues = Arrays.toString(fieldType.getEnumConstants());

            errorMessage = String.format("Invalid value '%s' for field '%s'. Please ensure the correct values are provided. Accepted values are: %s. The input is case and whitespace sensitive!",
                    invalidValue, fieldName, validValues);
        } else {
            // Default handling
            errorMessage = String.format("Invalid value '%s' for field '%s'. Please check the request body.", invalidValue, fieldName);
        }
        return errorMessage;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        ex.printStackTrace();  // Log the full stack trace
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
