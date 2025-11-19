package com.devops.agendamento.controller;

import com.devops.agendamento.exceptions.DateUnavailableException;
import com.devops.agendamento.exceptions.DuplicateRegisterException;
import com.devops.agendamento.exceptions.InvalidCredentialsException;
import com.devops.agendamento.exceptions.NotFoundResourceException;
import com.devops.agendamento.utils.ApiRespose;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<ApiRespose<?>> handleNotFoundResourceException(NotFoundResourceException e) {
        return new ResponseEntity<>(
                new ApiRespose<>(true, e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiRespose<?>> handleInvalidCredentialsException(InvalidCredentialsException e) {
        return new ResponseEntity<>(
                new ApiRespose<>(true, e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    public ResponseEntity<ApiRespose<?>> handleDuplicateRegisterException(DuplicateRegisterException e) {
        return new ResponseEntity<>(
                new ApiRespose<>(true, e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DateUnavailableException.class)
    public ResponseEntity<ApiRespose<?>> handleDateUnavailableException(DateUnavailableException e) {
        return new ResponseEntity<>(
                new ApiRespose<>(true, e.getMessage(), null),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiRespose<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(
                new ApiRespose<>(true, e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }
}
