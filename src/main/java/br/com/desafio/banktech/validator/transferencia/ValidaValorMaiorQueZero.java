package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.TransferenciaMenorOuIgualZeroException;
import br.com.desafio.banktech.model.Transferencia;

import java.math.BigDecimal;

public class ValidaValorMaiorQueZero implements IValidadorTransferencia{

    @Override
    public void validar(Transferencia transferencia) throws TransferenciaMenorOuIgualZeroException {
        if (transferencia.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new TransferenciaMenorOuIgualZeroException();
        }
    }
}
