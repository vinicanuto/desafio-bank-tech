package br.com.desafio.banktech.service;

import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.repository.TransferenciaRepository;
import br.com.desafio.banktech.threads.ExecutaTransferenciaThread;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
public class TransferenciaServiceTest {

    @TestConfiguration
    static class TransferenciaServiceTestConfiguration{

        @Bean
        public TransferenciaService transferenciaService(){
            return new TransferenciaService();
        };
    }

    @Autowired
    private TransferenciaService transferenciaService;

    @MockBean
    private TransferenciaRepository transferenciaRepository;

    private Conta conta1=null;

    private Conta conta2=null;

    @Before
    public void mockAll(){
        conta1=new Conta(55l,new BigDecimal("1000.0"));
        conta2=new Conta(66l,new BigDecimal("1000.0"));

        Transferencia transferencia = new Transferencia(conta1,
                conta2,
                new BigDecimal("800"));


        ExecutaTransferenciaThread thread = new ExecutaTransferenciaThread(transferencia);

        thread.start();

        conta1=new Conta(55l,new BigDecimal("1000.0"));
        conta2=new Conta(66l,new BigDecimal("1000.0"));
    }

    @Test
    public void transferirEntreContas(){

        transferenciaService
                .transferirSaldoEntreContas(conta1,
                        conta2,
                        new BigDecimal("800.0"));

        assertEquals(new BigDecimal("200.0"),conta1.getSaldo());
        assertEquals(new BigDecimal("1800.0"),conta2.getSaldo());
    }
}