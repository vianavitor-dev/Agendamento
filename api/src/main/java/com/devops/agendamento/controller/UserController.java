package com.devops.agendamento.controller;

import com.devops.agendamento.dto.response.UserIdentityDTO;
import com.devops.agendamento.model.User;
import com.devops.agendamento.service.UserService;
import com.devops.agendamento.utils.ApiRespose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/verify")
    public ResponseEntity<ApiRespose<Long>> verifyByUsername(@RequestParam String username) {
        var userId = service.existisByUsername(username);

        return ResponseEntity.ok(new ApiRespose<>(false, "", userId));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRespose<UserIdentityDTO>> login(@RequestParam String username, @RequestParam String password) {
        UserIdentityDTO identity = service.login(username, password);

        return ResponseEntity.ok(new ApiRespose<>(false, "Login concluido", identity));
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiRespose<?>> changePassword(@PathVariable Long id, @RequestParam(name="changeTo") String newPassword) {
        service.changePassword(id, newPassword);

        return ResponseEntity.ok(new ApiRespose<>(false, "", null));
    }
}
