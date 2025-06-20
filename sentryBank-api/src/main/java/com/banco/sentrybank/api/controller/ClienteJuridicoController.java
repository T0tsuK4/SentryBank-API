package com.banco.sentrybank.api.controller;

import com.banco.sentrybank.api.domain.model.ClienteFisico;
import com.banco.sentrybank.api.domain.model.ClienteJuridico;
import com.banco.sentrybank.api.domain.repository.ClienteJuridicoRepository;
import com.banco.sentrybank.api.domain.service.ClienteJuridicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/clientes-juridicos")
public class ClienteJuridicoController {

    private final ClienteJuridicoService clienteJuridicoService;

    @Autowired
    public ClienteJuridicoController(ClienteJuridicoService clienteJuridicoService) {
        this.clienteJuridicoService = clienteJuridicoService;
    }
    @GetMapping
    public ResponseEntity<List<ClienteJuridico>> listarTodos(){
        return ResponseEntity.ok(clienteJuridicoService.listarClienteJuridico());
    }

    @GetMapping("/juridico/{id}")
    public ResponseEntity<ClienteJuridico> buscarPorId (@PathVariable Long id){
        return  ResponseEntity.ok(clienteJuridicoService.buscarClienteJuridico(id));
    }

    @PostMapping
    public ResponseEntity<ClienteJuridico> salvarClienteJuridico(@RequestBody ClienteJuridico clienteJuridico){
        ClienteJuridico novoClienteJuridico = clienteJuridicoService.salvarClienteJuridico(clienteJuridico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteJuridico);
    }

    @PutMapping("/juridico/{id}")
    public ResponseEntity<ClienteJuridico> atualizarClienteJuridico(@PathVariable Long id, @RequestBody ClienteJuridico clienteJuridicoAtualizado){
        ClienteJuridico novoClienteJuridicoAtualizado = clienteJuridicoService.alterarClienteJuridico(id, clienteJuridicoAtualizado);
        return ResponseEntity.ok(novoClienteJuridicoAtualizado);
    }

    @DeleteMapping("/juridico/{id}")
    public ResponseEntity<Void> deletarClienteJuridico(@PathVariable Long id){
        clienteJuridicoService.deletarClienteJuridico(id);
        return ResponseEntity.noContent().build();
    }



}
