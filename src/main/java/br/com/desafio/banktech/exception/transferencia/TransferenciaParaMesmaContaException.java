package br.com.desafio.banktech.exception.transferencia;

import br.com.desafio.banktech.exception.BusinessException;

public class TransferenciaParaMesmaContaException extends BusinessException {
    private static final String MENSAGEM="Não é permitida a transferência entre mesma conta";
    public TransferenciaParaMesmaContaException() {
        super(MENSAGEM);
    }
}
