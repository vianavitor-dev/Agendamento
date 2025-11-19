package com.devops.agendamento.utils;

import com.devops.agendamento.model.Owner;
import com.devops.agendamento.repository.OwnerRepository;
import com.devops.agendamento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateOwnerCMDRunner implements CommandLineRunner {
    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        boolean alreadyExists = userRepository.findByUsername("admin").isPresent();

        if (alreadyExists) {
            return;
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        Owner owner = new Owner();
        owner.setName("Administrador");
        owner.setCellphoneNumber("(14) 96090-0002");
        owner.setUsername("admin");
        owner.setPassword(encoder.encode("123"));

        ownerRepository.save(owner);

        System.out.println("Administrator account registered");
    }
}
