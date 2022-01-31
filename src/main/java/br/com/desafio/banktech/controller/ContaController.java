package br.com.desafio.banktech.controller;

import br.com.desafio.banktech.constants.ApiVersion;
import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.exception.conta.ContaNaoEncontradaException;
import br.com.desafio.banktech.form.TransferenciaForm;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class ContaController {


    private static final String RECURSO="contas";

    private static final String V1_URL= ApiVersion.V1 + "/" + RECURSO;
    @Autowired
    private ContaService contaService;

    @PostMapping(V1_URL+"/{numeroConta}/saldo/transferir")
    @ResponseBody
    public ResponseEntity<Transferencia> buscarClientePorNumeroConta( @PathVariable("numeroConta") Long numeroConta,
            @RequestBody @Valid TransferenciaForm form)
            throws BusinessException, InterruptedException {
        Transferencia transferencia = contaService.transferirSaldo(numeroConta, form);


        return ResponseEntity.ok(transferencia);
    }

}
