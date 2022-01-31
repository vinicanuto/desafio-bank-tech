package br.com.desafio.banktech.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "numeroConta"}) })
public class Conta implements Serializable {

    @Id
    @Getter
    @Setter
    private Long numeroConta;
    @NotNull
    private BigDecimal saldo=BigDecimal.ZERO;

    public synchronized BigDecimal getSaldo() {
        return saldo;
    }

    public synchronized boolean debitar(BigDecimal valor){
        if(this.getSaldo().compareTo(valor) < 0)
            return false;
        this.saldo = this.getSaldo().subtract(valor);
        return true;
    }

    public synchronized boolean depositar(BigDecimal valor){
        this.saldo = this.getSaldo().add(valor);
        return true;
    }

    public synchronized boolean transferir(Conta contaDestino, BigDecimal valor){
        if(debitar(valor)){
            contaDestino.depositar(valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return numeroConta.equals(conta.numeroConta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroConta);
    }
}
