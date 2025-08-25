package org.example.tutorial.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ErrorResponse> notFound(NotFound ex){
        ErrorResponse errorResponse = new ErrorResponse();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", ex.getMessage());
        errorResponse.setMessage(errorMap);
        errorResponse.setErrorCode("404_NOT_FOUND");
        errorResponse.setTimestamp(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.add("error-code", "NOT_FOUND");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> badRequest(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("400_BAD_REQUEST");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );
        errorResponse.setMessage(fieldErrors);
        errorResponse.setTimestamp(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.add("error-code", "BAD_REQUEST");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(errorResponse);
    }

}
