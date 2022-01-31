package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
