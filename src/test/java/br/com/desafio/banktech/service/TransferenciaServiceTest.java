package br.com.desafio.banktech.service;

import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.StatusTransferencia;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.repository.ClienteRepository;
import br.com.desafio.banktech.repository.TransferenciaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

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

    @MockBean
    private ClienteRepository clienteRepository;

    @Before
    public void mockAll(){
        Cliente cliente1 =new Cliente("John", new Conta(55l,new BigDecimal("1000.0")));
        Cliente cliente2 =new Cliente("Doe", new Conta(66l,new BigDecimal("1000.0")));

        Mockito
                .when(clienteRepository.findByContaNumeroConta(cliente1.getConta().getNumeroConta()))
                .thenReturn(Optional.of(cliente1));

        Mockito
                .when(clienteRepository.findByContaNumeroConta(cliente2.getConta().getNumeroConta()))
                .thenReturn(Optional.of(cliente2));

        Transferencia transferencia = new Transferencia(cliente1.getConta(),
                cliente2.getConta(),
                new BigDecimal("800"));

        Transferencia transferenciaRetorno = transferencia;

        transferenciaRetorno.setStatusTransferencia(StatusTransferencia.SUCESSO);
        transferenciaRetorno.setDataCriacao(Calendar.getInstance());
        transferenciaRetorno.setDetalhes(null);

        Mockito
                .when(transferenciaRepository.save(transferencia)).thenReturn(transferenciaRetorno);
    }

    @Test
    public void transferirEntreContas(){

        Optional<Cliente> clienteOrigem = clienteRepository.findByContaNumeroConta(55l);
        Optional<Cliente> clienteDestino = clienteRepository.findByContaNumeroConta(66l);

        Transferencia transferencia = transferenciaService
                .transferirSaldoEntreContas(clienteOrigem.get().getConta(),
                        clienteDestino.get().getConta(),
                        new BigDecimal("800.0"));

        assertEquals(StatusTransferencia.SUCESSO,transferencia.getStatusTransferencia());
        assertEquals(new BigDecimal("800.0"),transferencia.getValor());
        assertEquals(clienteOrigem.get().getConta(),transferencia.getContaOrigem());
        assertEquals(clienteDestino.get().getConta(),transferencia.getContaDestino());
    }
}