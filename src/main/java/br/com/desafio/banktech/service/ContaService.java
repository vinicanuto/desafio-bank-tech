package br.com.desafio.banktech.service;

import br.com.desafio.banktech.form.TransferenciaForm;
import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.exception.conta.ContaNaoEncontradaException;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.StatusTransferencia;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransferenciaService transferenciaService;

    public Transferencia transferirSaldo(Long numeroContaOrigem, TransferenciaForm transferenciaForm)
            throws BusinessException {


        Conta contaOrigem = buscarConta(contaRepository,numeroContaOrigem);

        Conta contaDestino = buscarConta(contaRepository, transferenciaForm.getNumeroContaDestino());

        Transferencia resultado = transferenciaService
                .transferirSaldoEntreContas(contaOrigem,
                        contaDestino,
                        transferenciaForm.getValor());

        if(resultado.getStatusTransferencia().equals(StatusTransferencia.FALHA))
            throw new BusinessException(resultado.getDetalhes());

        return resultado;
    }

    private static Conta buscarConta(ContaRepository repository, Long numeroConta){
        Optional<Conta> contaOrigem = repository.findById(numeroConta);
        if(contaOrigem.isPresent()){
            return contaOrigem.get();
        }

        throw new ContaNaoEncontradaException(numeroConta);
    }
}
