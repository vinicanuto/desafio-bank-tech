package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByContaNumeroConta(Long numeroConta);
}
