package br.com.desafio.banktech.config.validacao;

import lombok.Getter;

@Getter
public class BusinessExceptionDTO {
    private String mensagem;

    public BusinessExceptionDTO(String mensagem) {
        this.mensagem=mensagem;
    }
}
