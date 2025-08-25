package org.example.tutorial.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private LocalDateTime timestamp;
    private Map<String, String> message;

}
