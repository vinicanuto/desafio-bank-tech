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

/**
 * @author vi.santos
 */
@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private TransferenciaService transferenciaService;


    /**
     * Dado conta para débito, conta para crédito, realiza transferencia
     * @param numeroContaDebito
     * @param transferenciaForm
     * @return Transferencia
     * @throws BusinessException
     */
    public Transferencia transferirSaldo(Long numeroContaDebito, TransferenciaForm transferenciaForm)
            throws BusinessException {


        Conta contaOrigem = buscarConta(contaRepository,numeroContaDebito);

        Conta contaDestino = buscarConta(contaRepository, transferenciaForm.getNumeroContaDestino());

        Transferencia resultado = transferenciaService
                .transferirSaldoEntreContas(contaOrigem,
                        contaDestino,
                        transferenciaForm.getValor());

        if(resultado.getStatusTransferencia().equals(StatusTransferencia.FALHA))
            throw new BusinessException(resultado.getDetalhes());

        return resultado;
    }

    /**
     * Busca conta por numero
     * @param repository
     * @param numeroConta
     * @return Conta
     */
    private static Conta buscarConta(ContaRepository repository, Long numeroConta){
        Optional<Conta> contaOrigem = repository.findById(numeroConta);
        if(contaOrigem.isPresent()){
            return contaOrigem.get();
        }

        throw new ContaNaoEncontradaException(numeroConta);
    }
}
