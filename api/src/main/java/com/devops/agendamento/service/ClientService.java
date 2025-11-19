package com.devops.agendamento.service;

import com.devops.agendamento.dto.request.ClientRegisterDTO;
import com.devops.agendamento.dto.response.ClientInfoCardDTO;
import com.devops.agendamento.exceptions.DuplicateRegisterException;
import com.devops.agendamento.exceptions.NotFoundResourceException;
import com.devops.agendamento.mapper.ClientDTOMapper;
import com.devops.agendamento.model.Client;
import com.devops.agendamento.repository.ClientRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public ClientInfoCardDTO getAsInfoCard(@NotNull Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Cliente não encontrado"));

        return ClientDTOMapper.toClientInfoCard(client);
    }

    public void register(@NotNull(message = "Preencha os campos") ClientRegisterDTO formData)
            throws NotFoundResourceException {

        boolean alreadyRegistered = clientRepository.existsByUsername(formData.username());

        if (alreadyRegistered) {
            throw new DuplicateRegisterException("Este username já esta em uso, insira outro");
        }

        String hashedPassword = encoder.encode(formData.password());

        Client client = ClientDTOMapper.toClient(formData);
        client.setPassword(hashedPassword);

        clientRepository.save(client);
    }

}
