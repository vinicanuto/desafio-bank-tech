package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.SaldoInsuficienteException;
import br.com.desafio.banktech.exception.transferencia.TransferenciaParaMesmaContaException;
import br.com.desafio.banktech.model.Transferencia;


/**
 * @author vi.santos
 */
public class ValidaSaldoSuficiente implements IValidadorTransferencia{

    /**
     * Caso valor da transferencia maior que saldo disponível na conta débito da erro
     * @param transferencia
     * @throws TransferenciaParaMesmaContaException
     */
    @Override
    public void validar(Transferencia transferencia) throws SaldoInsuficienteException {
        if (transferencia.getContaDebito().getSaldo().compareTo(transferencia.getValor()) <0){
            throw new SaldoInsuficienteException();
        }
    }
}
