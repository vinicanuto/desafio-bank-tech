package br.com.desafio.banktech.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    public boolean sacar(BigDecimal valor){
        synchronized (this){
            if(this.getSaldo().compareTo(valor) < 0)
                return false;
            this.saldo = this.getSaldo().subtract(valor);
            return true;
        }
    }

    public boolean depositar(BigDecimal valor){
        synchronized(this){
            this.saldo = this.getSaldo().add(valor);
            return true;
        }
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
