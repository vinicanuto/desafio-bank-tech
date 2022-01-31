package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.SaldoInsuficienteException;
import br.com.desafio.banktech.model.Transferencia;

public class ValidaSaldoSuficiente implements IValidadorTransferencia{

    @Override
    public void validar(Transferencia transferencia) throws SaldoInsuficienteException {
        if (transferencia.getContaOrigem().getSaldo().compareTo(transferencia.getValor()) <0){
            throw new SaldoInsuficienteException();
        }
    }
}
