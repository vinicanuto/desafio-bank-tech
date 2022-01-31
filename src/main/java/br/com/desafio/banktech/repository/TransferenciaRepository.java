package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    List<Transferencia> findAllByContaOrigemNumeroContaOrderByDataCriacaoDesc(Long contaOrigem);
}
