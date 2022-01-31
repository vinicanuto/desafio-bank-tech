package br.com.desafio.banktech.service;

import br.com.desafio.banktech.dto.ClienteDTO;
import br.com.desafio.banktech.exception.BusinessException;
import br.com.desafio.banktech.exception.conta.NumeroDeContaJaCadastradoException;
import br.com.desafio.banktech.model.Cliente;
import br.com.desafio.banktech.model.Conta;
import br.com.desafio.banktech.repository.ClienteRepository;
import br.com.desafio.banktech.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    public List<ClienteDTO> listarTodos(){
        return ClienteDTO.converteListaCliente(clienteRepository.findAll());
    }

    public Optional<Cliente> buscarPorNumeroConta(Long numeroConta) {
        return  clienteRepository.findByContaNumeroConta(numeroConta);
    }

    public Cliente cadastrar(Cliente cliente) throws NumeroDeContaJaCadastradoException {
        Optional<Conta> conta = contaRepository.findById(cliente.getConta().getNumeroConta());
        if(conta.isPresent())
            throw new NumeroDeContaJaCadastradoException(cliente.getConta().getNumeroConta());
        return clienteRepository.save(cliente);
    }
}
