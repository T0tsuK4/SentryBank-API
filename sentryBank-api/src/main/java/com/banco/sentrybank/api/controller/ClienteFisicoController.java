package com.banco.sentrybank.api.controller;


import com.banco.sentrybank.api.domain.model.Cliente;
import com.banco.sentrybank.api.domain.model.ClienteFisico;
import com.banco.sentrybank.api.domain.service.ClienteFisicoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/clientes-fisicos")
public class ClienteFisicoController {

    private final ClienteFisicoService clienteFisicoService;

    @Autowired
    public ClienteFisicoController(ClienteFisicoService clienteFisicoService) {
        this.clienteFisicoService = clienteFisicoService;
    }
    //REQUISIÇÃO HTTP GET: Lista todos os clientes fisicos
    @GetMapping
    public ResponseEntity<List<ClienteFisico>> listarTodos(){
        return ResponseEntity.ok(clienteFisicoService.listarClienteFisico());
    }
    @GetMapping("/fisico/{id}")
    public ResponseEntity<ClienteFisico> buscarPorId (@PathVariable Long id){
        return ResponseEntity.ok(clienteFisicoService.buscarClienteFisico(id));
    }

    private static final Logger logger = LoggerFactory.getLogger(ClienteFisicoController.class);

    @PostMapping // É a anotação que mapeia uma requisição POST. Que neste metodo está cadastrando um novo cliente físico
    public ResponseEntity<ClienteFisico> salvarClienteFisico(@Valid @RequestBody ClienteFisico clienteFisico){

        logger.info("Recebido, dados: {}", clienteFisico);

        ClienteFisico novoClienteFisico = clienteFisicoService.salvarClienteFisico(clienteFisico);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoClienteFisico);
    }

    @PutMapping("/fisico/{id}") //Metodo que atualiza dados de um cliente fisico
    public ResponseEntity<ClienteFisico> atualizarClienteFisico(@PathVariable Long id, @RequestBody ClienteFisico clienteFisicoAtualizado){
        ClienteFisico novoClienteFisicoAtualizado = clienteFisicoService.alterarClienteFisico(id, clienteFisicoAtualizado);
        return ResponseEntity.ok(novoClienteFisicoAtualizado);
    }

    @DeleteMapping("/fisico/{id}") //Metodo que busca e deleta o cliente fisico pelo id
    public ResponseEntity<Void> deletarClientFisico(@PathVariable Long id){
        clienteFisicoService.deletarClienteFisico(id);
        return ResponseEntity.noContent().build();
    }

}
