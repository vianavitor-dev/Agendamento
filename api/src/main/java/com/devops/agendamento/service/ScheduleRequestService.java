package com.devops.agendamento.service;

import com.devops.agendamento.dto.request.NewScheduleRequestDTO;
import com.devops.agendamento.dto.response.ScheduleRequestCardDTO;
import com.devops.agendamento.exceptions.DateUnavailableException;
import com.devops.agendamento.exceptions.NotFoundResourceException;
import com.devops.agendamento.mapper.ScheduleRequestDTOMapper;
import com.devops.agendamento.model.Client;
import com.devops.agendamento.model.RequestStatus;
import com.devops.agendamento.model.ScheduleRequest;
import com.devops.agendamento.repository.ClientRepository;
import com.devops.agendamento.repository.ScheduleRequestRepository;
import com.devops.agendamento.utils.SimpleCardTranslator;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleRequestService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ScheduleRequestRepository scheduleRequestRepository;

    public void sendRequest(@NotNull NewScheduleRequestDTO dto)
            throws NotFoundResourceException, DateUnavailableException {

        Instant now = Instant.now();
        LocalDate dateNow = LocalDateTime.ofInstant(
                now,
                ZoneId.of("America/Sao_Paulo")
        ).toLocalDate();

        // Verifica se as datas inseridas são antes da data atual
        if (dto.startDate().isBefore(dateNow) || dto.endDate().isBefore(dateNow)) {
            throw new IllegalArgumentException("Data inserida inválida, verifique-as");
        }

        // Verifica se alguém já agendou a piscina neste intervalo
        boolean isDateScheduled = scheduleRequestRepository
                .existisInInterval(dto.startDate(), dto.endDate());

        if (isDateScheduled) {
            throw new DateUnavailableException("Está data já foi marcada");
        }

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new NotFoundResourceException("Cliente não encontrado"));

        ScheduleRequest scheduleRequest = ScheduleRequestDTOMapper.toScheduleRequest(dto, client);
        scheduleRequest.setRequestedAt(dateNow);

        scheduleRequestRepository.save(scheduleRequest);
    }

    public List<ScheduleRequestCardDTO> getByClient(@NotNull Long clientId)
            throws NotFoundResourceException {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundResourceException("Cliente não encontrado"));

        return scheduleRequestRepository.findByClient(client)
                .stream()
                .map(scheduleRequest -> {
                    String interval = SimpleCardTranslator.getIntervalInPtBr(
                            scheduleRequest.getStartDate(), scheduleRequest.getEndDate()
                    );

                    return ScheduleRequestDTOMapper.toScheduleRequestCardDTO(scheduleRequest, interval);
                })
                .toList();
    }

    public List<ScheduleRequestCardDTO> filter
            (@NotNull Integer month, Optional<Integer> optionalDay, Optional<Integer> optionalYear) {

        // Caso o ano não seja informado pelo Proprietário busca o ano atual
        int year = optionalYear.orElseGet( () -> {
            Instant now = Instant.now();
            LocalDate dateNow = LocalDateTime.ofInstant(
                    now,
                    ZoneId.of("America/Sao_Paulo")
            ).toLocalDate();

            return dateNow.getYear();
        });

        List<ScheduleRequest> queryResult;

        if (optionalDay.isEmpty()) {
            queryResult = scheduleRequestRepository.findByMonthAndYear(month, year);
        } else {
            LocalDate date = LocalDate.of(year, month, optionalDay.get());
            queryResult = scheduleRequestRepository.findByStartDate(date).stream().toList();
        }

        return queryResult
                .stream()
                .map(scheduleRequest -> {
                    String interval = SimpleCardTranslator.getIntervalInPtBr(
                            scheduleRequest.getStartDate(), scheduleRequest.getEndDate()
                    );

                    return ScheduleRequestDTOMapper.toScheduleRequestCardDTO(scheduleRequest, interval);
                })
                .toList();
    }

    public void answerRequest(@NotNull Long id, @NotNull boolean accept)
            throws NotFoundResourceException {

        ScheduleRequest scheduleRequest = scheduleRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Agendamento não encontrado"));

        RequestStatus newStatus = accept ? RequestStatus.ACCEPTED : RequestStatus.REFUSED;
        scheduleRequest.setStatus(newStatus);

        scheduleRequestRepository.save(scheduleRequest);
    }

    public void rescheduleRequest(@NotNull Long id, @NotNull LocalDate newStartDate, LocalDate newEndDate)
            throws NotFoundResourceException, IllegalAccessException {

        Instant now = Instant.now();
        LocalDate dateNow = LocalDateTime.ofInstant(
                now,
                ZoneId.of("America/Sao_Paulo")
        ).toLocalDate();

        // Verifica se as datas inseridas são de antes da data atual
        if (newStartDate.isBefore(dateNow) || newEndDate.isBefore(dateNow)) {
            throw new IllegalArgumentException("Data inserida inválida, verifique-as");
        }

        ScheduleRequest scheduleRequest = scheduleRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Agendamento não encontrado"));

        scheduleRequest.setStartDate(newStartDate);
        scheduleRequest.setEndDate(newEndDate);

        scheduleRequestRepository.save(scheduleRequest);
    }

    public void cancelRequest(@NotNull Long id)
            throws NotFoundResourceException {

        ScheduleRequest scheduleRequest = scheduleRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Agendamento não encontrado"));

        scheduleRequest.setStatus(RequestStatus.CANCELED);
        scheduleRequestRepository.save(scheduleRequest);
    }
}
