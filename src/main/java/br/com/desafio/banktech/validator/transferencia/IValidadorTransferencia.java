package br.com.desafio.banktech.validator.transferencia;

import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.model.Transferencia;

public interface IValidadorTransferencia {

    void validar(final Transferencia transferencia) throws BusinessException;
}
