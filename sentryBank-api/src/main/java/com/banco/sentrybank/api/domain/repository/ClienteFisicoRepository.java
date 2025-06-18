package com.banco.sentrybank.api.domain.repository;

import com.banco.sentrybank.api.domain.model.Cliente;
import com.banco.sentrybank.api.domain.model.ClienteFisico;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClienteFisicoRepository extends JpaRepository<ClienteFisico, Long> {
    Optional<Cliente> findByCpf(String cpf);

}
