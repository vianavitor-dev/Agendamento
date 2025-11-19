package com.devops.agendamento.dto.request;

import java.time.LocalDate;

public record NewScheduleRequestDTO(
        LocalDate startDate,
        LocalDate endDate,
        Long clientId
) {
}
