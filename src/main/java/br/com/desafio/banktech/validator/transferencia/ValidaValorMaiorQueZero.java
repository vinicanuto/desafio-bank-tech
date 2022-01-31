package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.TransferenciaMenorOuIgualZeroException;
import br.com.desafio.banktech.exception.transferencia.TransferenciaParaMesmaContaException;
import br.com.desafio.banktech.model.Transferencia;

import java.math.BigDecimal;


/**
 * @author vi.santos
 */
public class ValidaValorMaiorQueZero implements IValidadorTransferencia{

    /**
     * Caso valor da transferencia menor ou igual a zero dá erro
     * @param transferencia
     * @throws TransferenciaParaMesmaContaException
     */
    @Override
    public void validar(Transferencia transferencia) throws TransferenciaMenorOuIgualZeroException {
        if (transferencia.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new TransferenciaMenorOuIgualZeroException();
        }
    }
}
