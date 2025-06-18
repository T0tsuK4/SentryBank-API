package com.banco.sentrybank.api.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente_juridico")
@Inheritance(strategy = InheritanceType.JOINED)
public class ClienteJuridico extends Cliente{

    @NotNull
    @Column(unique = true, name = "cnpj")
    @JsonProperty("cnpj")
    private String cnpj;

    @NotNull
    @Column(name = "nome_fantasia")
    @JsonProperty("nomeFantasia")
    private String nomeFantasia;

    @NotNull
    @Column(name = "data_criacao")
    @JsonProperty("dataCriacao")
    private LocalDate dataCriacao;
}
