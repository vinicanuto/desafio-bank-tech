package br.com.desafio.banktech.exception.conta;

import br.com.desafio.banktech.exception.BusinessException;

public class NumeroDeContaJaCadastradoException extends BusinessException {
    private static final String MENSAGEM="Numero de conta já cadastrado: %1s";
    public NumeroDeContaJaCadastradoException(Long numeroConta) {
        super(String.format(MENSAGEM,numeroConta));
    }
}
