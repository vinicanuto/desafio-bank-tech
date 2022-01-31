package br.com.desafio.banktech.dto;

import br.com.desafio.banktech.model.Transferencia;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TransferenciaDTO implements Serializable {

    private Long id;

    private Long contaOrigem;

    private Long contaDestino;

    private BigDecimal valor;

    private Calendar data;

    private String status;

    private String detalhes;


    public TransferenciaDTO(Transferencia transferencia){
        this.id =transferencia.getId();
        this.data =transferencia.getDataCriacao();
        this.status =transferencia.getStatusTransferencia().toString();
        this.detalhes =transferencia.getDetalhes();
        this.valor =transferencia.getValor();
        this.contaOrigem=transferencia.getContaDebito().getNumeroConta();
        this.contaDestino=transferencia.getContaCredito().getNumeroConta();
    }

    public static final List<TransferenciaDTO> converteLista(List<Transferencia> transferencias){
        return transferencias.stream().map(TransferenciaDTO::new).collect(Collectors.toList());
    }
}
