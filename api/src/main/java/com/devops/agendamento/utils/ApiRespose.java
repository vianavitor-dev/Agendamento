package com.devops.agendamento.utils;

public record ApiRespose <T> (
        boolean error,
        String message,
        T data
) {
}
