package br.com.desafio.banktech.form;

import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class ClienteForm {


    @NotNull @NotEmpty
    private String nome;

    @NotNull
    private Long numeroConta;


    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal saldo;

    public ClienteForm(String nome, Long numeroConta, BigDecimal saldo) {
        this.nome = nome;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public Cliente converte(){
        return new Cliente(this.nome, new Conta(this.numeroConta,this.saldo));
    }
}
