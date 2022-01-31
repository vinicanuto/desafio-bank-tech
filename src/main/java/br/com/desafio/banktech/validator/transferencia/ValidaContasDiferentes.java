package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.TransferenciaParaMesmaContaException;
import br.com.desafio.banktech.model.Transferencia;

/**
 * @author vi.santos
 */
public class ValidaContasDiferentes implements IValidadorTransferencia{

    /**
     * Caso contas de debito e credito iguais aciona erro
     * @param transferencia
     * @throws TransferenciaParaMesmaContaException
     */
    @Override
    public void validar(Transferencia transferencia) throws TransferenciaParaMesmaContaException {
        if(transferencia.getContaDebito().equals(transferencia.getContaCredito())){
            throw new TransferenciaParaMesmaContaException();
        }
    }
}
