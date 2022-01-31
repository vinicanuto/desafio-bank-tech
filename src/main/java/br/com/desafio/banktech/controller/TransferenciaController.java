package br.com.desafio.banktech.controller;

import br.com.desafio.banktech.constants.ApiVersion;
import br.com.desafio.banktech.dto.TransferenciaDTO;
import br.com.desafio.banktech.model.Transferencia;
import br.com.desafio.banktech.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferenciaController {

    private static final String RECURSO="transferencias";

    private static final String V1_URL= ApiVersion.V1 + "/" + RECURSO;

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping(V1_URL)
    @ResponseBody
    public ResponseEntity<List<TransferenciaDTO>> listarTransferencias(@RequestParam("numeroConta") Long numeroConta){
        List<Transferencia> transferencias =  transferenciaService.listarTransacoesPorNumeroConta(numeroConta);
        return ResponseEntity.ok(TransferenciaDTO.converteLista(transferencias));
    }
}
