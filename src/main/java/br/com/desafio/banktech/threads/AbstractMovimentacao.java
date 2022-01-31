package br.com.desafio.banktech.threads;

import java.math.BigDecimal;

public abstract class AbstractMovimentacao extends Thread {

    protected BigDecimal valor;

    public AbstractMovimentacao(String threadName) {
        super(threadName);
    }

    @Override
    public void start(){
        super.start();
        while(this.isAlive()){
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Esperando");
        }
    }

    public abstract boolean executadoComSucesso();
}
