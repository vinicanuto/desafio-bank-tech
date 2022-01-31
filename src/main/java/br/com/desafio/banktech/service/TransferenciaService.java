package br.com.desafio.banktech.service;

import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.StatusTransferencia;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.repository.TransferenciaRepository;
import br.com.desafio.banktech.validator.transferencia.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;


    private static final Set<IValidadorTransferencia> validacoes = new HashSet<>();

    static {
        validacoes.add(new ValidaLimiteMaximo());
        validacoes.add(new ValidaSaldoSuficiente());
        validacoes.add(new ValidaContasDiferentes());
        validacoes.add(new ValidaValorMaiorQueZero());
    }

    @Transactional
    public Transferencia transferirSaldoEntreContas(Conta contaOrigem, Conta contaDestino, BigDecimal valor){

        Transferencia transferencia = new Transferencia(contaOrigem, contaDestino, valor);

        validacoes.forEach(v -> {
            try {
                v.validar(transferencia);
            } catch (BusinessException e) {
                setFalhaTransferencia(transferencia,e);
            }
        });

        if (transferencia.getStatusTransferencia().equals(StatusTransferencia.FALHA)){
            return transferenciaRepository.save(transferencia);
        }

        synchronized (this){
            if(transferencia.executa()){
                transferenciaRepository.save(transferencia);
            };
        }
        return transferencia;
    }

    public List<Transferencia> listarTransacoesPorNumeroConta(Long numeroConta){
        return transferenciaRepository
                .findAllByContaOrigemNumeroContaOrderByDataCriacaoDesc(numeroConta);
    }

    private void setFalhaTransferencia(final  Transferencia transferencia, BusinessException e){
        transferencia.setStatusTransferencia(StatusTransferencia.FALHA);
        transferencia.setDetalhes(e.getMessage());
    }

}
