package com.devops.agendamento.controller;

import com.devops.agendamento.dto.request.ClientRegisterDTO;
import com.devops.agendamento.dto.response.ClientInfoCardDTO;
import com.devops.agendamento.service.ClientService;
import com.devops.agendamento.utils.ApiRespose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin("*")
public class ClientController {
    @Autowired
    private ClientService service;

    @PostMapping
    public ResponseEntity<ApiRespose<?>> register(@RequestBody ClientRegisterDTO formData) {
        service.register(formData);

        return new ResponseEntity<>(new ApiRespose<>(false, "", null), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/card")
    public ResponseEntity<ApiRespose<ClientInfoCardDTO>> viewInfoCard(@PathVariable Long id) {
        var clientInfoCard = service.getAsInfoCard(id);

        return ResponseEntity.ok(new ApiRespose<>(false, "", clientInfoCard));
    }
}
