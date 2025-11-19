package com.devops.agendamento.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.devops.agendamento.dto.response.UserIdentityDTO;
import com.devops.agendamento.exceptions.InvalidCredentialsException;
import com.devops.agendamento.exceptions.NotFoundResourceException;
import com.devops.agendamento.model.Client;
import com.devops.agendamento.model.Owner;
import com.devops.agendamento.model.User;
import com.devops.agendamento.repository.OwnerRepository;
import com.devops.agendamento.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Long existisByUsername(@NotNull String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundResourceException("Username inválido ou não existe"))
                .getId();
    }

    public UserIdentityDTO login(@NotNull String username, @NotNull String password)
            throws NotFoundResourceException, InvalidCredentialsException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundResourceException("Usuário não encontrado"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Username ou senha incorretos");
        }

        boolean isOwner = ownerRepository.existsById(user.getId());

        return new UserIdentityDTO(user.getId(), isOwner);
    }

    public void changePassword(@NotNull Long id, @NotNull String newPassword) throws NotFoundResourceException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundResourceException("Usuário não encontrado"));

        String newHashedPassword = encoder.encode(newPassword);
        user.setPassword(newHashedPassword);

        userRepository.save(user);
    }
}
