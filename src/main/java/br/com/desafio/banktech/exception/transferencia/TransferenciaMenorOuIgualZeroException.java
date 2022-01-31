package br.com.desafio.banktech.exception.transferencia;

import br.com.desafio.banktech.exception.BusinessException;

public class TransferenciaMenorOuIgualZeroException extends BusinessException {
    private static final String MENSAGEM="Só é permitida a transferência de valor positivo";
    public TransferenciaMenorOuIgualZeroException() {
        super(MENSAGEM);
    }
}
