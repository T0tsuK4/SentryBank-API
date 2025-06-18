package com.banco.sentrybank.api.domain.repository;

import com.banco.sentrybank.api.domain.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(String numeroConta);
    boolean existsByNumeroConta(String numeroConta);
    boolean existsByNumeroContaAndIdNot(String numeroConta, Long id);
}
