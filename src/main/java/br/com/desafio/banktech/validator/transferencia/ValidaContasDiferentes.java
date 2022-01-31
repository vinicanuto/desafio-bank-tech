package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.TransferenciaParaMesmaContaException;
import br.com.desafio.banktech.model.Transferencia;

public class ValidaContasDiferentes implements IValidadorTransferencia{
    @Override
    public void validar(Transferencia transferencia) throws TransferenciaParaMesmaContaException {
        if(transferencia.getContaOrigem().equals(transferencia.getContaDestino())){
            throw new TransferenciaParaMesmaContaException();
        }
    }
}
