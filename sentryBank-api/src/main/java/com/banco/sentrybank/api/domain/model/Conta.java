package com.banco.sentrybank.api.domain.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conta")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_agencia", nullable = false)
    private Agencia agencia;

    @NotNull
    @Column(name = "numero_conta", nullable = false, length = 20, unique = true)
    @JsonProperty("numero_conta")
    private String numeroConta;

    @NotNull
    @Column(name = "tipo_conta", nullable = false, length = 20)
    @JsonProperty("tipo_conta")
    private String tipoConta;// Corrente, Poupança, Salário

    @NotNull
    @Column(name = "saldo", nullable = false)
    @JsonProperty("saldo")
    private BigDecimal saldo = BigDecimal.ZERO;

    @NotNull
    @Column(name = "data_criacao", nullable = false)
    @JsonProperty("data_criacao")
    private LocalDate dataCriacao = LocalDate.now();

    @NotNull
    @Column(name = "ativa", nullable = false)
    @JsonProperty("ativa")
    private Boolean ativa = true;

    @OneToMany(mappedBy = "contaOrigem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoesOrigem;

    @OneToMany(mappedBy = "contaDestino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoesDestino;

    public void depositar(BigDecimal valor){
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor de depósito deve ser maior doque zero");
        }
        if (!this.ativa){
            throw new IllegalArgumentException("Não é possivel depositar em uma conta inativa");
        }
        this.saldo = this.saldo.add(valor);
    }
    public void sacar(BigDecimal valor){
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <=0){
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        if(!this.ativa){
            throw new IllegalArgumentException("Não é possivel sacar de uma conta inativa");
        }
        this.saldo = this.saldo.subtract(valor);
    }
    public void tranferir(Conta destino, BigDecimal valor){
        if (destino == null){
            throw new IllegalArgumentException("A conta de destino é obrigatória");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw  new IllegalArgumentException("O valor de transferencia deve ser maior que zero.");
        }
        if (this.saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para a transferência.");
        }

        this.saldo = this.saldo.subtract(valor);
        destino.setSaldo(destino.getSaldo().add(valor));
    }

    public List<Transacao> listarTransacoes(){
        List<Transacao> transacaos = new ArrayList<>();
        transacaos.addAll(this.transacoesOrigem);
        transacaos.addAll(this.transacoesDestino);
        return transacaos;
    }

}
