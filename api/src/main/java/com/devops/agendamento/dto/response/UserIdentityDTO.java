package com.devops.agendamento.dto.response;

public record UserIdentityDTO(
        Long id,
        boolean isOwner
) {
}
