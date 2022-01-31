package br.com.desafio.banktech.exception;

import javax.persistence.EntityNotFoundException;

public class RecursoNaoEncontradoException extends EntityNotFoundException {
    private static final String MENSAGEM="Entidade de ID: %1s não encontrado";
    public RecursoNaoEncontradoException(String identificador) {
        super(String.format(MENSAGEM,identificador));
    }
}
