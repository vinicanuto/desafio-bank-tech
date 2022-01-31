package br.com.desafio.banktech.exception.transferencia;

import br.com.desafio.banktech.exception.BusinessException;

public class SaldoInsuficienteException extends BusinessException {
    private static final String MENSAGEM="Saldo insuficiente para esta operação";
    public SaldoInsuficienteException() {
        super(MENSAGEM);
    }
}
