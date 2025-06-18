package com.banco.sentrybank.api.controller;


import com.banco.sentrybank.api.domain.model.Conta;
import com.banco.sentrybank.api.domain.model.Transacao;
import com.banco.sentrybank.api.domain.service.ContaService;
import com.banco.sentrybank.api.domain.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ContaService contaService;

    @Autowired
    public TransacaoController(TransacaoService transacaoService, ContaService contaService) {
        this.transacaoService = transacaoService;
        this.contaService = contaService;
    }

    public ResponseEntity<List<Transacao>> listarTransacoes(){
        return ResponseEntity.ok(transacaoService.listarTransacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarTransacaoPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(transacaoService.buscarTransacaoPorId(id));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Transacao> salvarTransacao(@Valid @RequestBody Transacao transacao){
        return ResponseEntity.ok(transacaoService.salvarTransacao(transacao));
    }

    @PostMapping("/transferir")
    public ResponseEntity<Transacao> registrarTransferencia(
            @RequestParam Long idOrigem,
            @RequestParam Long idDestino,
            @RequestParam BigDecimal valor){
        Conta contaOrigem = contaService.buscarContas(idOrigem);
        Conta contaDestino = contaService.buscarContas(idDestino);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transacaoService.registrarTransferencia(contaOrigem, contaDestino, valor));
    }
}
