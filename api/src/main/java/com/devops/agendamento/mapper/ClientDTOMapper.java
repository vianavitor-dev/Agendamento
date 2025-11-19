package com.devops.agendamento.mapper;

import com.devops.agendamento.dto.request.ClientRegisterDTO;
import com.devops.agendamento.dto.response.ClientInfoCardDTO;
import com.devops.agendamento.model.Client;

public class ClientDTOMapper {
    public static Client toClient(ClientRegisterDTO dto) {
        Client client = new Client();
        client.setName(dto.name());
        client.setCellphoneNumber(dto.cellphoneNumber());
        client.setUsername(dto.username());
        client.setPassword(dto.password());

        return client;
    }

    public static ClientInfoCardDTO toClientInfoCard(Client client) {
        return new ClientInfoCardDTO(
                client.getUsername(),
                client.getName(),
                client.getCellphoneNumber()
        );
    }
}
