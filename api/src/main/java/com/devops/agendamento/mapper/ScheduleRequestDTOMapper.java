package com.devops.agendamento.mapper;

import com.devops.agendamento.dto.request.NewScheduleRequestDTO;
import com.devops.agendamento.dto.response.ScheduleRequestCardDTO;
import com.devops.agendamento.model.Client;
import com.devops.agendamento.model.ScheduleRequest;

public class ScheduleRequestDTOMapper {
    public static ScheduleRequest toScheduleRequest(NewScheduleRequestDTO dto, Client client) {
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.setStartDate(dto.startDate());
        scheduleRequest.setEndDate(dto.endDate());
        scheduleRequest.setClient(client);

        return scheduleRequest;
    }

    public static ScheduleRequestCardDTO toScheduleRequestCardDTO(ScheduleRequest scheduleRequest, String interval) {
        String price = String.format("R$ %.2f", scheduleRequest.getPrice());

        return new ScheduleRequestCardDTO(
                scheduleRequest.getId(),
                scheduleRequest.getStartDate(),
                scheduleRequest.getClient().getId(),
                interval,
                price,
                scheduleRequest.getStatus().getStatusBr()
        );
    }
}
