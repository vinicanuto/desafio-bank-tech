package br.com.desafio.banktech.dto;

import br.com.desafio.banktech.model.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    public ClienteDTO(Cliente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.numeroConta = cliente.getConta().getNumeroConta();
        this.saldo = cliente.getConta().getSaldo();
    }

    private Long id;

    private String nome;

    @JsonProperty("numeroConta")
    private Long numeroConta;

    @JsonProperty("saldo")
    private BigDecimal saldo;

    public static List<ClienteDTO> converteListaCliente(List<Cliente> clienteList){
        return clienteList.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }
}
