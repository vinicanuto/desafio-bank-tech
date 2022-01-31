package br.com.desafio.banktech;

import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.exception.transferencia.SaldoInsuficienteException;
import br.com.desafio.banktech.exception.transferencia.TransferenciaMenorOuIgualZeroException;
import br.com.desafio.banktech.exception.transferencia.TransferenciaParaMesmaContaException;
import br.com.desafio.banktech.exception.transferencia.ValorLimiteUltrapassadoException;
import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.validator.transferencia.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

		if(cliente2.getConta().sacar(valorSaque)){
			assertEquals(valorEsperado, cliente2.getConta().getSaldo());
		}
	}

	@Test
	public void sacarSaldoInsuficiente(){

		BigDecimal saldoAtual = cliente2.getConta().getSaldo();
		BigDecimal valorSaque = saldoAtual.add(new BigDecimal("500.0"));

		assertFalse(this.cliente2.getConta().sacar(valorSaque));
		assertEquals(saldoAtual,cliente2.getConta().getSaldo());
	}

	@Test
	public void testeDepositarSacar(){

		BigDecimal saldoAtual = cliente2.getConta().getSaldo();
		BigDecimal valor = saldoAtual.add(new BigDecimal("500.0"));

		cliente2.getConta().depositar(valor);
		cliente2.getConta().sacar(valor);

		assertEquals(saldoAtual,cliente2.getConta().getSaldo());
	}


}
