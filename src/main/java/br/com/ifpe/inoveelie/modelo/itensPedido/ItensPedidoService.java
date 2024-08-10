package br.com.ifpe.inoveelie.modelo.itensPedido;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service

public class ItensPedidoService {
    
   @Autowired
   private ItensPedidoRepository repository;

   @Transactional
   public ItensPedido save(ItensPedido itensPedido) {

       itensPedido.setHabilitado(Boolean.TRUE);
       itensPedido.setVersao(1L);
       itensPedido.setDataCriacao(LocalDate.now());
       return repository.save(itensPedido);
   }

   @Transactional
   public void update(Long id, ItensPedido itensPedidoAlterado) {
 
      ItensPedido itensPedido = repository.findById(id).get();
      itensPedido.setMateriais(itensPedidoAlterado.getMateriais());
      itensPedido.setPedidos(itensPedidoAlterado.getPedidos());
      itensPedido.setQtd(itensPedidoAlterado.getQtd());
      itensPedido.setValor(itensPedidoAlterado.getValor());
         
      itensPedido.setVersao(itensPedido.getVersao() + 1);
      repository.save(itensPedido);
   }

   public List<ItensPedido> listarTodos() {

      return repository.findAll();
   }

   public ItensPedido obterPorID(Long id) {

      return repository.findById(id).get();
   }

   @Transactional
   public void delete(Long id) {
 
       ItensPedido itensPedido = repository.findById(id).get();
       itensPedido.setHabilitado(Boolean.FALSE);
       itensPedido.setVersao(itensPedido.getVersao() + 1);
 
       repository.save(itensPedido);
   }
}
