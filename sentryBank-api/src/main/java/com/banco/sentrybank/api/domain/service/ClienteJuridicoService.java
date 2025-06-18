package com.banco.sentrybank.api.domain.service;

import com.banco.sentrybank.api.domain.model.ClienteJuridico;
import com.banco.sentrybank.api.domain.repository.ClienteJuridicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteJuridicoService {

    private final ClienteJuridicoRepository clienteJuridicoRepository;

    @Autowired
    public ClienteJuridicoService(ClienteJuridicoRepository clienteJuridicoRepository) {
        this.clienteJuridicoRepository = clienteJuridicoRepository;
    }

    public List<ClienteJuridico> listarClienteJuridico(){
        return clienteJuridicoRepository.findAll();
    }

    public ClienteJuridico buscarClienteJuridico(Long id){
        return clienteJuridicoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Cliente Juridico não encontrado pelo id:" + id));
    }

    public ClienteJuridico salvarClienteJuridico(ClienteJuridico clienteJuridico){
        return clienteJuridicoRepository.save(clienteJuridico);
    }

    public ClienteJuridico alterarClienteJuridico(Long id, ClienteJuridico clienteJuridicoAtualizado){
        ClienteJuridico clienteJuridicoExistente = buscarClienteJuridico(id);
        clienteJuridicoExistente.setNome(clienteJuridicoAtualizado.getNome());
        clienteJuridicoExistente.setEmail(clienteJuridicoAtualizado.getEmail());
        clienteJuridicoExistente.setSenha(clienteJuridicoAtualizado.getSenha());
        clienteJuridicoExistente.setCnpj(clienteJuridicoAtualizado.getCnpj());
        clienteJuridicoExistente.setNomeFantasia(clienteJuridicoAtualizado.getNomeFantasia());
        clienteJuridicoExistente.setDataCriacao(clienteJuridicoAtualizado.getDataCriacao());

        return clienteJuridicoRepository.save(clienteJuridicoExistente);
    }

    public void deletarClienteJuridico(Long id){
        clienteJuridicoRepository.deleteById(id);
    }
}
