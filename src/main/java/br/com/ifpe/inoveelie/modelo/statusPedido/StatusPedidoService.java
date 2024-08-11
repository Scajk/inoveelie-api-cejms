package br.com.ifpe.inoveelie.modelo.statusPedido;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service

public class StatusPedidoService {

    @Autowired
    private StatusPedidoRepository repository;

    @Transactional
    public StatusPedido save(StatusPedido statusPedido) {

        statusPedido.setHabilitado(Boolean.TRUE);
        statusPedido.setVersao(1L);
        statusPedido.setDataCriacao(LocalDate.now());
        return repository.save(statusPedido);
    }

    @Transactional
    public void update(Long id, StatusPedido statusPedidoAlterado) {
 
       StatusPedido statusPedido = repository.findById(id).get();
       statusPedido.setEmAndamento(statusPedidoAlterado.getEmAndamento());
       statusPedido.setCancelado(statusPedidoAlterado.getCancelado());
       statusPedido.setFinalizado(statusPedidoAlterado.getFinalizado());
         
       statusPedido.setVersao(statusPedido.getVersao() + 1);
       repository.save(statusPedido);
   }

   public List<StatusPedido> listarTodos() {

    return repository.findAll();
   }

   public StatusPedido obterPorID(Long id) {

    return repository.findById(id).get();
   }

   @Transactional
    public void delete(Long id) {

       StatusPedido statusPedido = repository.findById(id).get();
       statusPedido.setHabilitado(Boolean.FALSE);
       statusPedido.setVersao(statusPedido.getVersao() + 1);

       repository.save(statusPedido);
   }
}