package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Dado numero de conta retorna o Cliente titular
     * @param numeroConta
     * @return Optional<Cliente>
     */
    Optional<Cliente> findByContaNumeroConta(Long numeroConta);
}
