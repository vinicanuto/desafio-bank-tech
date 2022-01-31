package br.com.desafio.banktech.integration;

import br.com.desafio.banktech.constants.ApiVersion;
import br.com.desafio.banktech.dto.ClienteDTO;
import br.com.desafio.banktech.dto.TransferenciaDTO;
import br.com.desafio.banktech.form.ClienteForm;
import br.com.desafio.banktech.form.TransferenciaForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class BankTechV1IntegrationTests {


    private static String VERSION = ApiVersion.V1;

    private static String URL_VERSION="/api/"+VERSION+"/";

    private static String CLIENTE_ENDPOINT=URL_VERSION+"clientes";

    private static String TRANSFERENCIA_ENDPOINT=URL_VERSION+"transferencias";

    private static String CONTA_ENDPOINT=URL_VERSION+"contas";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void criarClienteComConta() {


        ClienteForm clienteForm = new ClienteForm("Cesar",111l,new BigDecimal(10000.0));
        ResponseEntity<String> cliente1 = testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,String.class);


        assertEquals(HttpStatus.CREATED,cliente1.getStatusCode());

    }


    @Test
    public void buscarClientePorNumeroConta() {

        ClienteForm clienteForm2 = new ClienteForm("Cesar",888l,new BigDecimal(100.0));
        ResponseEntity<String> cliente2 = testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm2,String.class);

        assertEquals(HttpStatus.CREATED,cliente2.getStatusCode());

        Map<String,Long> params= new HashMap<>();

        params.put("numeroConta",clienteForm2.getNumeroConta());

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(CLIENTE_ENDPOINT,String.class,params);

        assertTrue(responseEntity.hasBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }


    @Test
    public void cadastraContaDuplicada() throws Exception {

        ClienteForm clienteForm = new ClienteForm("John",333l,new BigDecimal(100.0));

        ResponseEntity<String> cliente1 = testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,String.class);

        assertEquals(HttpStatus.CREATED,cliente1.getStatusCode());

        ResponseEntity<String> cliente2 = testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,String.class);

        assertEquals(HttpStatus.BAD_REQUEST,cliente2.getStatusCode());
    }


    @Test
    public void realizaTransferenciaSucesso()  {

        ClienteForm clienteForm = new ClienteForm("John",1234l,new BigDecimal(100.0));
        ClienteForm clienteForm2 = new ClienteForm("Marie",4321l,new BigDecimal(100.0));

        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,ClienteDTO.class);
        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm2,ClienteDTO.class);

        TransferenciaForm transferenciaForm = new TransferenciaForm(4321l,new BigDecimal(50.0) );

        ResponseEntity<String> transferencia = testRestTemplate
                .postForEntity(CONTA_ENDPOINT+"/1234/saldo/transferir",
                        transferenciaForm,
                        String.class);

        assertEquals(HttpStatus.OK, transferencia.getStatusCode());
    }

    @Test
    public void errorAoRealizaTransferenciaSemSaldo()  {

        ClienteForm clienteForm = new ClienteForm("John",1234l,new BigDecimal(100.0));
        ClienteForm clienteForm2 = new ClienteForm("Marie",4321l,new BigDecimal(100.0));

        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,ClienteDTO.class);
        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm2,ClienteDTO.class);

        TransferenciaForm transferenciaForm = new TransferenciaForm(4321l,new BigDecimal(200.0) );

        ResponseEntity<String> transferencia = testRestTemplate
                .postForEntity(CONTA_ENDPOINT+"/1234/saldo/transferir",
                        transferenciaForm,
                        String.class);

        assertEquals(HttpStatus.BAD_REQUEST, transferencia.getStatusCode());
    }

    @Test
    public void errorAoRealizaTransferenciaMaiorQueMil()  {

        ClienteForm clienteForm = new ClienteForm("John",1234l,new BigDecimal(10000.0));
        ClienteForm clienteForm2 = new ClienteForm("Marie",4321l,new BigDecimal(100.0));

        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,ClienteDTO.class);
        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm2,ClienteDTO.class);

        TransferenciaForm transferenciaForm = new TransferenciaForm(4321l,new BigDecimal(1001.0) );

        ResponseEntity<String> transferencia = testRestTemplate
                .postForEntity(CONTA_ENDPOINT+"/1234/saldo/transferir",
                        transferenciaForm,
                        String.class);

        assertEquals(HttpStatus.BAD_REQUEST, transferencia.getStatusCode());
    }


    @Test
    public void listaTransferenciasDeUmaContaPorDataCriacaoDecrescente()  {

        ClienteForm clienteForm = new ClienteForm("John",9876l,new BigDecimal(200.0));
        ClienteForm clienteForm2 = new ClienteForm("Marie",8887l,new BigDecimal(100.0));

        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm,ClienteDTO.class);
        testRestTemplate
                .postForEntity(CLIENTE_ENDPOINT, clienteForm2,ClienteDTO.class);

        TransferenciaForm transferenciaForm = new TransferenciaForm(8887l,new BigDecimal(50.0) );

        ResponseEntity<String> transferencia = testRestTemplate
                .postForEntity(CONTA_ENDPOINT+"/{contaOrigem}/saldo/transferir",
                        transferenciaForm,
                        String.class,
                        clienteForm.getNumeroConta());

        assertEquals(HttpStatus.OK, transferencia.getStatusCode());

        TransferenciaForm transferenciaForm2 = new TransferenciaForm(8887l,new BigDecimal(85.0) );

        ResponseEntity<String> transferencia2 = testRestTemplate
                .postForEntity(CONTA_ENDPOINT+"/{contaOrigem}/saldo/transferir",
                        transferenciaForm2,
                        String.class,
                        clienteForm.getNumeroConta());

        assertEquals(HttpStatus.OK, transferencia2.getStatusCode());

        ResponseEntity<TransferenciaDTO[]> transferenciaResponse =
                testRestTemplate.getForEntity(TRANSFERENCIA_ENDPOINT+"?numeroConta={numeroConta}",
                        TransferenciaDTO[].class,
                        clienteForm.getNumeroConta());

        assertEquals(HttpStatus.OK,
                transferenciaResponse.getStatusCode());

        List<TransferenciaDTO> transferenciaDTOList = Arrays.stream(transferenciaResponse.getBody()).collect(Collectors.toList());
        assertEquals(2, transferenciaDTOList.size());
        assertTrue(transferenciaDTOList.get(0).getData().after(transferenciaDTOList.get(1).getData()));
    }


}
