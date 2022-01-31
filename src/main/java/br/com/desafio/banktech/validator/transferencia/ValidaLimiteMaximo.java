package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.transferencia.ValorLimiteUltrapassadoException;
import br.com.desafio.banktech.model.Transferencia;

import java.math.BigDecimal;

public class ValidaLimiteMaximo implements IValidadorTransferencia{

    public static final BigDecimal VALOR_MAXIMO_TRANSFERENCIA=new BigDecimal(1000.00);
    @Override
    public  void validar(Transferencia transferencia) throws ValorLimiteUltrapassadoException {
        if (transferencia.getValor().compareTo(VALOR_MAXIMO_TRANSFERENCIA) > 0){
            throw new ValorLimiteUltrapassadoException();
        }
    }
}
