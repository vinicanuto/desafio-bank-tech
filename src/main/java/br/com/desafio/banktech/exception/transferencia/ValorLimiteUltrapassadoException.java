package br.com.desafio.banktech.exception.transferencia;

import br.com.desafio.banktech.exception.BusinessException;

public class ValorLimiteUltrapassadoException extends BusinessException {
    private static final String MENSAGEM="Ultrapassou o limite permitido por transferencia";
    public ValorLimiteUltrapassadoException() {
        super(MENSAGEM);
    }
}
