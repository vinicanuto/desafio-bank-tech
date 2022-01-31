package br.com.desafio.banktech.exception.conta;

import javax.persistence.EntityNotFoundException;

public class ContaNaoEncontradaException extends EntityNotFoundException {
    private static final String MENSAGEM="Não foi possível localizar conta de número : %1s";

    public ContaNaoEncontradaException(Long numeroConta) {
        super(String.format(MENSAGEM, numeroConta));
    }
}
