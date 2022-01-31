package br.com.desafio.banktech.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Cliente implements Serializable {

	public Cliente(String nome, Conta conta) {
		this.nome = nome;
		this.conta = conta;
	}

	public Cliente(Long id, String nome, Conta conta) {
		this.id = id;
		this.nome = nome;
		this.conta = conta;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;

	@JoinColumn(unique=true)
	@OneToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	private Conta conta;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return id.equals(cliente.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
