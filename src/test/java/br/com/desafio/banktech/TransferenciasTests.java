package br.com.desafio.banktech;

import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.exception.transferencia.SaldoInsuficienteException;
import br.com.desafio.banktech.exception.transferencia.TransferenciaMenorOuIgualZeroException;
import br.com.desafio.banktech.exception.transferencia.TransferenciaParaMesmaContaException;
import br.com.desafio.banktech.exception.transferencia.ValorLimiteUltrapassadoException;
import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.threads.ExecutaTransferenciaThread;
import br.com.desafio.banktech.validator.transferencia.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransferenciasTests {

	private Cliente cliente1 = null;
	private Cliente cliente2 = null;

	@BeforeEach
	public void mockAll(){
		cliente1 = new Cliente("João", new Conta(1l,new BigDecimal("2000.0")));
		cliente2 = new Cliente("Pedro", new Conta(2l,new BigDecimal("300.0")));
	}

	@Test
	public void erroTransferenciaMaiorQueLimitePermitido(){
		BigDecimal valorLimite = ValidaLimiteMaximo.VALOR_MAXIMO_TRANSFERENCIA;
		valorLimite =valorLimite.add(new BigDecimal("1.0"));


		Transferencia transferencia = new Transferencia(cliente1.getConta(),
				cliente2.getConta(),
				valorLimite
				);

		assertThrows(ValorLimiteUltrapassadoException.class,
				() ->new ValidaLimiteMaximo().validar(transferencia));
	}

	@Test
	public void erroTransferenciaSaldoInsuficiente(){
		Transferencia transferencia = new Transferencia(cliente2.getConta(),
				cliente1.getConta(),
				new BigDecimal("400.0"));

		assertThrows(SaldoInsuficienteException.class,
				() ->new ValidaSaldoSuficiente().validar(transferencia));
	}

	@Test
	public void erroTransferenciaContaOrigemIgualDestino(){

		Transferencia transferencia = new Transferencia(cliente1.getConta(),
				cliente1.getConta(),
				new BigDecimal("400.0"));

		assertThrows(TransferenciaParaMesmaContaException.class,
				() ->new ValidaContasDiferentes().validar(transferencia));
	}

	@Test
	public void erroTransferenciaValorIgualZero(){

		Transferencia transferencia = new Transferencia(cliente1.getConta(),
				cliente2.getConta(),
				new BigDecimal("0.0"));

		assertThrows(TransferenciaMenorOuIgualZeroException.class,
				() ->new ValidaValorMaiorQueZero().validar(transferencia));
	}

	@Test
	public void erroTransferenciaValorMenorQueZero(){
		Transferencia transferencia = new Transferencia(cliente1.getConta(),
				cliente2.getConta(),
				new BigDecimal("-15.0"));

		assertThrows(TransferenciaMenorOuIgualZeroException.class,
				() ->new ValidaValorMaiorQueZero().validar(transferencia));
	}

	@Test
	public void transferenciaSemErrosDeValidacao(){
		boolean valido=true;
		Set<IValidadorTransferencia> validacoes = new HashSet<>();

			validacoes.add(new ValidaLimiteMaximo());
			validacoes.add(new ValidaSaldoSuficiente());
			validacoes.add(new ValidaContasDiferentes());
			validacoes.add(new ValidaValorMaiorQueZero());

		Transferencia transferencia = new Transferencia(cliente1.getConta(),
				cliente2.getConta(),
				new BigDecimal("50.0"));

		for (IValidadorTransferencia v : validacoes) {
			try {
				v.validar(transferencia);
			} catch (BusinessException e) {
				valido = false;
				e.printStackTrace();
			}
		}
		assertTrue(valido);
	}


	@Test
	public void depositar200SaldoConta(){

		BigDecimal saldoAtual = cliente2.getConta().getSaldo();
		BigDecimal valorDeposito = new BigDecimal("200.0");
		BigDecimal valorEsperado = saldoAtual.add(valorDeposito);

		if(cliente2.getConta().depositar(valorDeposito)){
			assertEquals(valorEsperado, cliente2.getConta().getSaldo());
		}
	}

	@Test
	public void sacarSaldoConta(){

		BigDecimal saldoAtual = cliente2.getConta().getSaldo();
		BigDecimal valorSaque = new BigDecimal("200.0");
		BigDecimal valorEsperado = saldoAtual.subtract(valorSaque);

		if(cliente2.getConta().debitar(valorSaque)){
			assertEquals(valorEsperado, cliente2.getConta().getSaldo());
		}
	}

	@Test
	public void sacarSaldoInsuficiente(){

		BigDecimal saldoAtual = cliente2.getConta().getSaldo();
		BigDecimal valorSaque = saldoAtual.add(new BigDecimal("500.0"));

		assertFalse(this.cliente2.getConta().debitar(valorSaque));
		assertEquals(saldoAtual,cliente2.getConta().getSaldo());
	}

	@Test
	public void testeDepositarSacar(){

		BigDecimal saldoAtual = cliente2.getConta().getSaldo();
		BigDecimal valor = saldoAtual.add(new BigDecimal("500.0"));

		cliente2.getConta().depositar(valor);
		cliente2.getConta().debitar(valor);

		assertEquals(saldoAtual,cliente2.getConta().getSaldo());
	}

	@Test
	public void testaConcorrencia(){
		Vector<ExecutaTransferenciaThread> threads = new Vector<>();

		Conta c1 = new Conta(55l,new BigDecimal("1000.0"));
		Conta c2 = new Conta(66l,new BigDecimal("1000.0"));

		Transferencia tf1 = new Transferencia(c1,
				c2,
				new BigDecimal("100.0"));

		ExecutaTransferenciaThread et1 = new ExecutaTransferenciaThread(tf1);
		ExecutaTransferenciaThread et2 = new ExecutaTransferenciaThread(tf1);
		ExecutaTransferenciaThread et3 = new ExecutaTransferenciaThread(tf1);
		ExecutaTransferenciaThread et4 = new ExecutaTransferenciaThread(tf1);

		//c1 =1000 - 400 =600
		//c2 =1000 + 400 =1400
		Transferencia tf2 = new Transferencia(c2,
				c1,
				new BigDecimal("50.0"));

		ExecutaTransferenciaThread et5 = new ExecutaTransferenciaThread(tf2);
		ExecutaTransferenciaThread et6 = new ExecutaTransferenciaThread(tf2);
		ExecutaTransferenciaThread et7 = new ExecutaTransferenciaThread(tf2);
		ExecutaTransferenciaThread et8 = new ExecutaTransferenciaThread(tf2);

		//c1 =600 + 200 =800
		//c2 =1400 - 200 =1200

		threads.addAll(Arrays.asList(et1,et2,et3,et4,et5,et6,et7,et8));

		threads.forEach(t-> t.start());

		assertEquals(new BigDecimal("800.0"), c1.getSaldo());
		assertEquals(new BigDecimal("1200.0"), c2.getSaldo());
	}


}
