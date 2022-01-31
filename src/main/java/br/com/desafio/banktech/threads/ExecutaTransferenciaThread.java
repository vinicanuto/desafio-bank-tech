package br.com.desafio.banktech.threads;

import br.com.desafio.banktech.model.StatusTransferencia;
import br.com.desafio.banktech.model.Transferencia;

/**
 * @author vi.santos
 * @version 1.0
 */
public class ExecutaTransferenciaThread extends AbstractMovimentacao {


    private Transferencia transferencia;

    public ExecutaTransferenciaThread(Transferencia tf){
        super(tf.getContaDebito().getNumeroConta().toString());
        this.transferencia=tf;
        super.valor=transferencia.getValor();
    }

    @Override
    public void run() {
        if(transferencia.getContaDebito()
                .transferir(transferencia.getContaCredito(),valor)){
            transferencia.setStatusTransferencia(StatusTransferencia.SUCESSO);
        }else {
            transferencia.setStatusTransferencia(StatusTransferencia.FALHA);
        };
        super.run();
    }

    @Override
    public boolean executadoComSucesso() {
        return transferencia.getStatusTransferencia().equals(StatusTransferencia.SUCESSO);
    }
}
