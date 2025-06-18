package com.banco.sentrybank.api.domain.service;


import com.banco.sentrybank.api.domain.model.Agencia;
import com.banco.sentrybank.api.domain.model.Cliente;
import com.banco.sentrybank.api.domain.model.Conta;
import com.banco.sentrybank.api.domain.repository.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;

    @Autowired
    public AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    public List<Agencia> listarAgencias(){
        return agenciaRepository.findAll();
    }

    public Agencia buscarAgencia(Long id){
        return agenciaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Agencia não encontrada pelo id" +id));
    }

    public Agencia salvarAgencia(Agencia agencia){
        if(agenciaRepository.existsByNumero((agencia.getNumero()))){
            throw new IllegalArgumentException("O numero da agência: " + agencia.getNumero() + "já está em uso!");
        }
        return agenciaRepository.save(agencia);
    }

    public Agencia atualizarAgencia(Long id, Agencia agenciaAtualizada){
        Agencia agenciaExistente = buscarAgencia(id);
        agenciaExistente.setNome(agenciaAtualizada.getNome());
        agenciaExistente.setNumero(agenciaExistente.getNumero());
        agenciaExistente.setTelefone(agenciaAtualizada.getTelefone());
        //validação
        if (agenciaRepository.existsByNumeroAndIdNot(agenciaExistente.getNumero(), id)){
            throw new IllegalArgumentException("O numero da agência "
                    + agenciaExistente.getNumero() + " já está em uso por outra agência!");
        }
        return agenciaRepository.save(agenciaExistente);
    }

    public void deletarAgencia(Long id){
        Agencia agencia = buscarAgencia(id);
        if (!agencia.getContas().isEmpty()){
            throw new IllegalArgumentException("Não é possivel deletar uma agência com contas vinculadas");
        }
        agenciaRepository.deleteById(id);
    }

    @Transactional
    public Conta abrirConta(Long idAgencia, Cliente cliente, String tipoConta){
        Agencia agencia = buscarAgencia(idAgencia);
        Conta novaConta = agencia.abrirConta(cliente, tipoConta);
        return agenciaRepository.save(agencia).getContas().get(agencia.getContas().size() - 1);
    }

    public List<Conta> listarContasAtivas(Long idAgencia){
        List<Conta> todasContas = listarContasAtivas(idAgencia);
        return todasContas.stream().filter(Conta::getAtiva).collect(Collectors.toList());
    }
}
