package com.banco.sentrybank.api.domain.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    @JsonProperty("nome")
    private String nome;

    @Column(name = "numero", nullable = false, length = 10, unique = true)
    @JsonProperty("numero")
    private String numero;

    @Column(name = "telefone", length = 15)
    @JsonProperty("telefone")
    private String telefone;

    @OneToMany(mappedBy = "agencia")
    private List<Conta> contas;

    public Conta abrirConta(Cliente cliente, String tipoConta){
        if (cliente == null){
            throw new IllegalArgumentException("O cliente é obrigatório!");
        }
        if (tipoConta == null){
            throw new IllegalArgumentException("Tipo de conta inválido Use: Corrente, Poupança, Salário!");
        }
        String numeroConta = gerarNumeroContaUnico();
        Conta novaConta = new Conta();
        novaConta.setAgencia(this);
        novaConta.setNumeroConta(numeroConta);
        novaConta.setTipoConta(tipoConta);
        novaConta.setSaldo(BigDecimal.ZERO);
        novaConta.setAtiva(true);

        contas.add(novaConta);
        return novaConta;
    }

    public List<Conta> listarContas(){
        return new ArrayList<>(this.contas);
    }

    private String gerarNumeroContaUnico() {
        //usa o ID da agência + um contador interno (ajuste para produção)
        int contador = contas.size() + 1;
        return this.numero + "-" + String.format("%04d", contador); // Ex.: 0001-0001
    }
}
