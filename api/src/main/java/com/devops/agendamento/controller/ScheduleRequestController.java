package com.devops.agendamento.controller;

import com.devops.agendamento.dto.request.NewScheduleRequestDTO;
import com.devops.agendamento.dto.response.ScheduleRequestCardDTO;
import com.devops.agendamento.service.ScheduleRequestService;
import com.devops.agendamento.utils.ApiRespose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedule-requests")
@CrossOrigin("*")
public class ScheduleRequestController {
    @Autowired
    private ScheduleRequestService service;

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<ApiRespose<List<ScheduleRequestCardDTO>>> getByClient(@PathVariable Long clientId) {
        var result = service.getByClient(clientId);

        return ResponseEntity.ok(new ApiRespose<>(false, "Lista de Agendamento do Cliente", result));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiRespose<List<ScheduleRequestCardDTO>>> filter(
            @RequestParam int month,
            @RequestParam(required = false) Integer day,
            @RequestParam(required = false) Integer year) {

        var result = service.filter(month, Optional.ofNullable(day), Optional.ofNullable(year));

        return ResponseEntity.ok(new ApiRespose<>(false, "Lista de Agendamentos", result));
    }

    @PostMapping
    public ResponseEntity<ApiRespose<?>> send(@RequestBody NewScheduleRequestDTO formData) {
        service.sendRequest(formData);

        return new ResponseEntity<>(new ApiRespose<>(false, "Enviado com Sucesso", null), HttpStatus.CREATED);
    }

    @PutMapping("/reschedule/{id}")
    public ResponseEntity<ApiRespose<?>> reschedule(
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) throws IllegalAccessException {

        service.rescheduleRequest(id, startDate, endDate);

        return new ResponseEntity<>(new ApiRespose<>(false, "Reagendado com sucesso", null), HttpStatus.CREATED);
    }

    @PatchMapping("/answer/{id}")
    public ResponseEntity<ApiRespose<?>> answer(@PathVariable Long id, @RequestParam Boolean accept) {
        service.answerRequest(id, accept);

        return ResponseEntity.ok(new ApiRespose<>(false, "Respondido com sucesso", null));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<ApiRespose<?>> cancel(@PathVariable Long id) {
        service.cancelRequest(id);

        return ResponseEntity.ok(new ApiRespose<>(false, "Agendamento cancelado!", null));
    }
}
