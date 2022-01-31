package br.com.desafio.banktech.repository;

import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.threads.ExecutaTransferenciaThread;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TransferenciaRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Before
    public void popularDados(){
        Cliente cliente1 =new Cliente("John", new Conta(55l,new BigDecimal("1000.0")));
        Cliente cliente2 =new Cliente("Doe", new Conta(66l,new BigDecimal("1000.0")));
        clienteRepository.saveAll(Arrays.asList(cliente1,cliente2));

        Transferencia transferencia1 = new Transferencia(cliente1.getConta(),
                cliente2.getConta(),
                new BigDecimal("100.0"));

        ExecutaTransferenciaThread executaTransferenciaThread = new ExecutaTransferenciaThread(transferencia1);
        executaTransferenciaThread.start();

        transferenciaRepository.save(transferencia1);

        Transferencia transferencia2 = new Transferencia(cliente1.getConta(),
                cliente2.getConta(),
                new BigDecimal("150.0"));

        ExecutaTransferenciaThread executaTransferenciaThread2 = new ExecutaTransferenciaThread(transferencia1);
        executaTransferenciaThread2.start();

        transferenciaRepository.save(transferencia2);
    }

    @Test
    public void deveListarTransferenciasPorDataDecrescente(){
        List<Transferencia> transferenciaList = transferenciaRepository
                .findAllByContaOrigemNumeroContaOrderByDataCriacaoDesc(55l);

        assertEquals(2, transferenciaList.size());
        assertEquals(new BigDecimal("150.0"), transferenciaList.get(0).getValor());
        assertTrue(transferenciaList.get(0).getDataCriacao().after(transferenciaList.get(1).getDataCriacao()));
    }
}