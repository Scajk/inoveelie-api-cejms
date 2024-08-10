package br.com.ifpe.inoveelie.modelo.pedido;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service

public class PedidoService {
    
   @Autowired
   private PedidoRepository repository;

   @Transactional
   public Pedido save(Pedido pedido) {

       pedido.setHabilitado(Boolean.TRUE);
       pedido.setVersao(1L);
       pedido.setDataCriacao(LocalDate.now());
       return repository.save(pedido);
   }

    @Transactional
    public void update(Long id, Pedido pedidoAlterado) {
 
       Pedido pedido = repository.findById(id).get();
       pedido.setClientes(pedidoAlterado.getClientes());
       pedido.setStatusPedidos(pedidoAlterado.getStatusPedidos());
       pedido.setUsuarios(pedidoAlterado.getUsuarios());
       pedido.setMedidas(pedidoAlterado.getMedidas());
       pedido.setDescricao(pedidoAlterado.getDescricao());
       pedido.setDataEntrega(pedidoAlterado.getDataEntrega());
       pedido.setTipoPedido(pedidoAlterado.getTipoPedido());
         
       pedido.setVersao(pedido.getVersao() + 1);
       repository.save(pedido);
   }

    public List<Pedido> listarTodos() {

        return repository.findAll();
    }

    public Pedido obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {
 
        Pedido pedido = repository.findById(id).get();
        pedido.setHabilitado(Boolean.FALSE);
        pedido.setVersao(pedido.getVersao() + 1);
 
        repository.save(pedido);
    }
}