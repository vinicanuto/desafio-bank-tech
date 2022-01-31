package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Before
    public void popularDados(){
        Cliente cliente1 =new Cliente("John", new Conta(55l,new BigDecimal("1000.0")));
        Cliente cliente2 =new Cliente("Doe", new Conta(66l,new BigDecimal("1000.0")));
        clienteRepository.saveAll(Arrays.asList(cliente1,cliente2));
    }

    @Test
    public void deveEncontrarClientePorNumeroConta(){
        Optional<Cliente> cliente =  clienteRepository.findByContaNumeroConta(66l);

        assertTrue(cliente.isPresent());
        assertEquals("Doe",cliente.get().getNome());
    }
}