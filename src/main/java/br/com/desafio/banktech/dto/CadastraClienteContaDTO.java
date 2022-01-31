package br.com.desafio.banktech.dto;

import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CadastraClienteContaDTO implements Serializable {

    @JsonProperty("nome")
    private String nomeCliente;

    @JsonProperty("numeroConta")
    private Long numeroConta;

    @JsonProperty("saldo")
    private BigDecimal saldoConta;

    public Cliente converterParaCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome(this.nomeCliente);
        cliente.setConta(this.converteParaConta());
        return cliente;
    }

    public Conta converteParaConta(){
        Conta conta = new Conta();
        conta.setNumeroConta(this.numeroConta);
        conta.depositar(this.saldoConta);
        return conta;
    }
}
