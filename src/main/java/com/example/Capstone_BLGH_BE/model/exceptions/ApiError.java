package com.example.Capstone_BLGH_BE.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus status;
}

