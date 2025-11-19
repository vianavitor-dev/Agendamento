package com.devops.agendamento.dto.response;

import java.time.LocalDate;

public record ScheduleRequestCardDTO(
        Long id,
        LocalDate startDate,
        Long clientId,      // Usuário que fez o pedido
        String interval,    // examplo: 'Apenas {Sábado}' ou '{Sábado} até {Domingo}'
        String price,       // examplo: 'R$ 500.00'
        String status       // examplo: pendente, aceito, recusado, cancelado
) {
}
