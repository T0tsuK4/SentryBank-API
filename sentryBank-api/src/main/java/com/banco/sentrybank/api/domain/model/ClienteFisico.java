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
@Table(name = "cliente_fisico")
public class ClienteFisico extends Cliente{


    @NotNull
    @Column(name = "cpf")
    @JsonProperty("cpf")
    private String cpf;

    @NotNull
    @Column(name = "data_nascimento")
    @JsonProperty("dataNascimento")
    private LocalDate dataNascimento;


}
