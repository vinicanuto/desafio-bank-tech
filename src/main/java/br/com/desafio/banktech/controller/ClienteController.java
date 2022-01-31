package br.com.desafio.banktech.controller;

import br.com.desafio.banktech.constants.ApiVersion;
import br.com.desafio.banktech.dto.ClienteDTO;
import br.com.desafio.banktech.exception.conta.ContaNaoEncontradaException;
import br.com.desafio.banktech.exception.conta.NumeroDeContaJaCadastradoException;
import br.com.desafio.banktech.form.ClienteForm;
import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * @author vi.santos
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class ClienteController {

    private static final String RECURSO="clientes";

    private static final String V1_URL= ApiVersion.V1 + "/" + RECURSO;

    @Autowired
    private ClienteService clienteService;

    @GetMapping(V1_URL)
    @ResponseBody
    public List<ClienteDTO> listarTodos(){
        return clienteService.listarTodos();
    }

    @GetMapping(value = V1_URL, params = "numeroConta")
    @ResponseBody
    public ResponseEntity<ClienteDTO> buscarPorNumeroConta(@RequestParam("numeroConta") @NotNull Long numeroConta){
        Optional<Cliente> cliente = clienteService.buscarPorNumeroConta(numeroConta);

        if(!cliente.isPresent()){
            throw new ContaNaoEncontradaException(numeroConta);
        }
        return ResponseEntity.ok(new ClienteDTO(cliente.get()));
    }

    @PostMapping(V1_URL)
    @ResponseBody
    @Transactional
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody @Valid ClienteForm clienteForm,
                                                UriComponentsBuilder uriBuilder)
            throws NumeroDeContaJaCadastradoException {

        ClienteDTO clienteDTO = new ClienteDTO(clienteService.cadastrar(clienteForm.converte()));

        URI uri = uriBuilder.path(V1_URL+"/clientes/{id}").buildAndExpand(clienteDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(clienteDTO);
    }
}
