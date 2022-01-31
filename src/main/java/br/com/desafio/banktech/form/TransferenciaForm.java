package br.com.desafio.banktech.form;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class TransferenciaForm implements Serializable {

    @NotNull
    private Long numeroContaDestino;

    @DecimalMin(value = "0.01")
    private BigDecimal valor;

    public TransferenciaForm(Long numeroContaDestino, BigDecimal valor) {
        this.numeroContaDestino = numeroContaDestino;
        this.valor = valor;
    }
}
