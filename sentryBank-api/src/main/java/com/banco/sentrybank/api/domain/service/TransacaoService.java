package com.banco.sentrybank.api.domain.service;


import com.banco.sentrybank.api.domain.model.Conta;
import com.banco.sentrybank.api.domain.model.Transacao;
import com.banco.sentrybank.api.domain.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;

    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository, ContaService contaService) {
        this.transacaoRepository = transacaoRepository;
        this.contaService = contaService;
    }

    public List<Transacao> listarTransacoes(){
        return transacaoRepository.findAll();
    }

    public Transacao buscarTransacaoPorId(Long id){
        return transacaoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Transação não encontrada pelo id " + id));
    }

    public Transacao salvarTransacao(Transacao transacao) {
        if (transacao.getContaOrigem() == null) {
            throw new IllegalArgumentException("Conta de origem é obrigatória.");
        }
        if (transacao.getValor() == null || transacao.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transação inválido.");
        }
        if (transacao.getTipo() == null || transacao.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo da transação é obrigatório.");
        }
        return transacaoRepository.save(transacao);
    }

    public Transacao registrarTransferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valor){
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(contaOrigem);
        transacao.setContaDestino(contaDestino);
        transacao.setTipo("Transferência");
        transacao.setValor(valor);
        transacao.setDataTransacao(LocalDateTime.now());
        return salvarTransacao(transacao);
    }
}
