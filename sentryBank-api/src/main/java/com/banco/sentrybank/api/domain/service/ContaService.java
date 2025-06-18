package com.banco.sentrybank.api.domain.service;

import com.banco.sentrybank.api.domain.model.Agencia;
import com.banco.sentrybank.api.domain.model.Cliente;
import com.banco.sentrybank.api.domain.model.Conta;
import com.banco.sentrybank.api.domain.model.Transacao;
import com.banco.sentrybank.api.domain.repository.AgenciaRepository;
import com.banco.sentrybank.api.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ContaService {
    private final ContaRepository contaRepository;
    private final AgenciaRepository agenciaRepository;

    @Autowired
    public ContaService(ContaRepository contaRepository, AgenciaRepository agenciaRepository) {
        this.contaRepository = contaRepository;
        this.agenciaRepository = agenciaRepository;
    }

    public List<Conta> listarContas(){
        return contaRepository.findAll();
    }

    public Conta buscarContas(Long id){
        return contaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Conta não encontrada pelo id:" + id));
    }
    public Conta salvarConta(Conta conta){
        if (contaRepository.existsByNumeroConta(conta.getNumeroConta())) {
            System.out.println("1");
            throw new IllegalArgumentException("O número da conta " + conta.getNumeroConta() + " já está em uso.");
        }

        if (conta.getAgencia() == null) { //Valida Agencia
            System.out.println("2");
            throw new IllegalArgumentException("A agência é obrigatória.");
        }
        Long agenciaId = conta.getAgencia().getId();
        if (agenciaId == null) {
            System.out.println("3");
            throw new IllegalArgumentException("A agência deve ter um ID válido.");
        }

        Agencia agencia = agenciaRepository.findById(conta.getAgencia().getId())
                .orElseThrow(() -> new RuntimeException("Agência não encontrada com ID: " + conta.getAgencia().getId()));

        conta.setAgencia(agencia);
        return contaRepository.save(conta);
    }

    public Conta alterarConta(Long id, Conta contaAlterada){
        Conta contaExistente = buscarContas(id);
        contaExistente.setNumeroConta(contaAlterada.getNumeroConta());
        contaExistente.setTipoConta(contaAlterada.getTipoConta());
        contaExistente.setSaldo(contaAlterada.getSaldo());
        contaExistente.setAtiva(contaAlterada.getAtiva());

        return contaRepository.save(contaExistente);
    }

    public void deletarConta(Long id) {
        Conta conta = buscarContas(id);
        if (!conta.getAtiva()) {
            throw new IllegalStateException("Não é possível deletar uma conta inativa.");
        }
        if (!conta.getTransacoesOrigem().isEmpty() || !conta.getTransacoesDestino().isEmpty()) {
            throw new IllegalStateException("Não é possível deletar a conta com transações associadas.");
        }
        contaRepository.deleteById(id);
    }

    @Transactional
    public Conta depositar(Long id, BigDecimal valor){
        Conta conta = buscarContas(id);
        conta.depositar(valor);
        return contaRepository.save(conta);
    }
    @Transactional
    public Conta sacar(Long id, BigDecimal valor){
        Conta conta = buscarContas(id);
        conta.sacar(valor);
        return contaRepository.save(conta);
    }

    public Conta transferir(Long idOrigem, Long idDestino, BigDecimal valor) {
        Conta contaOrigem = buscarContas(idOrigem);
        Conta contaDestino = buscarContas(idDestino);
        contaOrigem.tranferir(contaDestino, valor);
        contaRepository.save(contaOrigem);
        return contaRepository.save(contaDestino);
    }

    public List<Transacao> listarTransacoes(Long id){
        Conta conta = buscarContas(id);
        return conta.listarTransacoes();
    }
}
