package com.banco.sentrybank.api.domain.service;

import com.banco.sentrybank.api.domain.model.Cliente;
import com.banco.sentrybank.api.domain.model.ClienteFisico;
import com.banco.sentrybank.api.domain.repository.ClienteFisicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteFisicoService {


    private final ClienteFisicoRepository clienteFisicoRepository;

    @Autowired
    public ClienteFisicoService(ClienteFisicoRepository clienteFisicoRepository) {;
        this.clienteFisicoRepository = clienteFisicoRepository;
    }

    public List<ClienteFisico> listarClienteFisico(){
        return clienteFisicoRepository.findAll();
    }

    public ClienteFisico buscarClienteFisico(Long id){
        return clienteFisicoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cliente fisico não encontrado pelo id:" + id));
    }

    public ClienteFisico salvarClienteFisico(ClienteFisico clienteFisico){ // Salva Cliente fisico junto com Cliete
        return clienteFisicoRepository.save(clienteFisico);
    }

    public ClienteFisico alterarClienteFisico(Long id, ClienteFisico clienteFisicoAtualizado){
        ClienteFisico clienteFisicoExistente = buscarClienteFisico(id);
        clienteFisicoExistente.setNome(clienteFisicoAtualizado.getNome());
        clienteFisicoExistente.setEmail(clienteFisicoAtualizado.getEmail());
        clienteFisicoExistente.setSenha(clienteFisicoAtualizado.getSenha());

        return clienteFisicoRepository.save(clienteFisicoExistente);
    }

    public void deletarClienteFisico(Long id){
        clienteFisicoRepository.deleteById(id); //Deleta o cliente cadastrado buscando pelo ID
    }

}

