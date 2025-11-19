package com.devops.agendamento.repository;

import com.devops.agendamento.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    boolean existsByUsername(String username);
}
