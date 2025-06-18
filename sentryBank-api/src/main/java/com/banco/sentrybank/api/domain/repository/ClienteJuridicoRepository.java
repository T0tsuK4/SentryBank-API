package com.banco.sentrybank.api.domain.repository;

import com.banco.sentrybank.api.domain.model.ClienteJuridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteJuridicoRepository extends JpaRepository<ClienteJuridico, Long> {
}
