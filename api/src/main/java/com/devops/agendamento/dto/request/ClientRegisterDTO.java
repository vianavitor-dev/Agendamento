package com.devops.agendamento.dto.request;

public record ClientRegisterDTO (
        String name,
        String cellphoneNumber,
        String username,
        String password
) {
}
