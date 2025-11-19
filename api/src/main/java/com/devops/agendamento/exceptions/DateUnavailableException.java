package com.devops.agendamento.exceptions;

public class DateUnavailableException extends RuntimeException {
    public DateUnavailableException(String message) {
        super(message);
    }
}
