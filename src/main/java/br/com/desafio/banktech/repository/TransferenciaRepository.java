package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {


    /**
     * Dado numero de conta retorna lista de transações ordenada por data de criação decrescente
     * @param contaDebito
     * @return List<Transferencia>
     */
    List<Transferencia> findAllByContaOrigemNumeroContaOrderByDataCriacaoDesc(Long contaDebito);
}
