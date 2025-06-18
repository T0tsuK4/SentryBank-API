package com.banco.sentrybank.api.controller;


import com.banco.sentrybank.api.domain.model.Agencia;
import com.banco.sentrybank.api.domain.model.Cliente;
import com.banco.sentrybank.api.domain.model.Conta;
import com.banco.sentrybank.api.domain.service.AgenciaService;
import com.banco.sentrybank.api.domain.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencias")
public class AgenciaController {

    private final AgenciaService agenciaService;
    private final ContaService contaService;

    @Autowired
    public AgenciaController(AgenciaService agenciaService, ContaService contaService) {
        this.agenciaService = agenciaService;
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<Agencia>> listarAgencias(){
        return ResponseEntity.ok(agenciaService.listarAgencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agencia> buscarAgenciaPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(agenciaService.buscarAgencia(id));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public  ResponseEntity<Agencia> salvarAgencia(@RequestBody Agencia agencia){
        try{
            return ResponseEntity.ok(agenciaService.salvarAgencia(agencia));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agencia> atualizarAgencia(@PathVariable Long id,@RequestBody Agencia agenciaAtualizada){
        try {
            return ResponseEntity.ok(agenciaService.atualizarAgencia(id, agenciaAtualizada));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgencia(@PathVariable Long id){
        agenciaService.deletarAgencia(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idAgencia}/contas")
    public ResponseEntity<Conta> abrirConta(@PathVariable Long idAgencia,
                                            @RequestBody Cliente cliente,@RequestParam String tipoConta){
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaService.abrirConta(idAgencia, cliente, tipoConta));
    }

    @GetMapping("/{idAgencia}/contas")
    public ResponseEntity<List<Conta>> listarContas(@PathVariable Long idAgencia){
        try {
            return ResponseEntity.ok(agenciaService.listarContasAtivas(idAgencia));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idAgencia}/contas/ativas")
    public ResponseEntity<List<Conta>> listarContasAtivas(@PathVariable Long idAgencia) {
        try {
            return ResponseEntity.ok(agenciaService.listarContasAtivas(idAgencia));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
