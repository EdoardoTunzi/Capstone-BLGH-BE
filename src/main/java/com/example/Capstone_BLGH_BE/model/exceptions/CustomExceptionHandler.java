package com.example.Capstone_BLGH_BE.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.BAD_REQUEST);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(EventDuplicateException.class)
    public ResponseEntity<ApiError> handleEventDuplicateException(EventDuplicateException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.CONFLICT);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.UNAUTHORIZED);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UsernameDuplicateException.class)
    public ResponseEntity<ApiError> handleUsernameDuplicateException(UsernameDuplicateException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.CONFLICT);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ApiError> handleEmailDuplicateException(EmailDuplicateException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.CONFLICT);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(CreateTokenException.class)
    public ResponseEntity<ApiError> handleCreateTokenException(CreateTokenException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ex.printStackTrace();
        ApiError apiError = new ApiError("Errore interno del server ", HttpStatus.INTERNAL_SERVER_ERROR);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

}

