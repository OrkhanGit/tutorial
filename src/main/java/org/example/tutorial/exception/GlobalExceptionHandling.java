package org.example.tutorial.exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(NotFoundId.class)
    public ResponseEntity<ErrorResponse> notFoundIdFromDB(NotFoundId ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorCode("404_NOT_FOUND");
        errorResponse.setTimestamp(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.add("error-code", "NOT_FOUND_ID_FROM_DB");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(errorResponse);
    }

    @ExceptionHandler(NotFoundTitle.class)
    public  ResponseEntity<ErrorResponse> notFoundTitleFromDB(NotFoundTitle ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorCode("404_NOT_FOUND");
        errorResponse.setTimestamp(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.add("error-code", "NOT_FOUND_TITLE_FROM_DB");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(errorResponse);
    }

    @ExceptionHandler(NotFoundList.class)
    public ResponseEntity<ErrorResponse> notFoundList(NotFoundList ex){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorCode("404_NOT_FOUND");
        errorResponse.setTimestamp(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.add("error-code", "NOT_FOUND_LIST_FROM_DB");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .headers(headers)
                .body(errorResponse);
    }

}
