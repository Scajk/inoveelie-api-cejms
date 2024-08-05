package br.com.ifpe.inoveelie.modelo.cliente;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service

public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente) {

        cliente.setHabilitado(Boolean.TRUE);
        cliente.setVersao(1L);
        cliente.setDataCriacao(LocalDate.now());
        return repository.save(cliente);
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado) {
 
       Cliente cliente = repository.findById(id).get();
       cliente.setNomeCliente(clienteAlterado.getNomeCliente());
       cliente.setNumeroCliente(clienteAlterado.getNumeroCliente());
         
       cliente.setVersao(cliente.getVersao() + 1);
       repository.save(cliente);
   }
 

    public List<Cliente> listarTodos() {

        return repository.findAll();
    }

    public Cliente obterPorID(Long id) {

        return repository.findById(id).get();
    }
    
    @Transactional
   public void delete(Long id) {

       Cliente cliente = repository.findById(id).get();
       cliente.setHabilitado(Boolean.FALSE);
       cliente.setVersao(cliente.getVersao() + 1);

       repository.save(cliente);
   }
}
