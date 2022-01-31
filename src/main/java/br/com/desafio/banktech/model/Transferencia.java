package br.com.desafio.banktech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Transferencia {

    public Transferencia(Conta contaOrigem, Conta contaDestino, BigDecimal valor){

        Objects.requireNonNull(contaOrigem,"Conta de origem não pode ser nula");
        Objects.requireNonNull(contaDestino,"Conta de destino não pode ser nula");
        Objects.requireNonNull(valor, "Valor da transferência é mandatório");

        this.contaOrigem=contaOrigem;
        this.contaDestino=contaDestino;
        this.valor=valor;
        this.statusTransferencia = StatusTransferencia.CRIADA;
        this.dataCriacao =Calendar.getInstance();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaOrigem;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaDestino;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCriacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusTransferencia statusTransferencia;

    private String detalhes;

    @NotNull
    private BigDecimal valor;

}
