package br.com.desafio.banktech.config.validacao;

import lombok.Getter;

@Getter
public class ErroValidacaoDTO {

    private String atributo;

    private String erro;

    public ErroValidacaoDTO(String atributo, String erro) {
        this.atributo = atributo;
        this.erro = erro;
    }
}
