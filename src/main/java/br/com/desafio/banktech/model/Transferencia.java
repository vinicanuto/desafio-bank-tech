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

    public Transferencia(Conta contaDebito, Conta contaCredito, BigDecimal valor){

        Objects.requireNonNull(contaDebito,"Conta de debito não pode ser nula");
        Objects.requireNonNull(contaCredito,"Conta de credito não pode ser nula");
        Objects.requireNonNull(valor, "Valor da transferência é mandatório");

        this.contaDebito=contaDebito;
        this.contaCredito=contaCredito;
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
    private Conta contaDebito;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Conta contaCredito;

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
