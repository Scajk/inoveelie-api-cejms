package br.com.ifpe.inoveelie.modelo.pedido;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.inoveelie.modelo.mensagens.EmailService;
import jakarta.transaction.Transactional;

@Service

public class PedidoService {
    
   @Autowired
   private PedidoRepository repository;

   @Autowired
    private EmailService emailService;

   @Transactional
   public Pedido save(Pedido pedido) {

       pedido.setHabilitado(Boolean.TRUE);
       pedido.setVersao(1L);
       pedido.setDataCriacao(LocalDate.now());
       
       Pedido pedidoCriado = repository.save(pedido);

       // 1. Envio de Comporvante
       emailService.enviarComprovante(pedidoCriado);

       return pedidoCriado;
   }

    @Transactional
    public void update(Long id, Pedido pedidoAlterado) {
 
       Pedido pedido = repository.findById(id).get();
       pedido.setTipo(pedidoAlterado.getTipo());
       pedido.setNomeCliente(pedidoAlterado.getNomeCliente());
       pedido.setNumeroCliente(pedidoAlterado.getNumeroCliente());
       pedido.setDataEntrega(pedidoAlterado.getDataEntrega());
       pedido.setValor(pedidoAlterado.getValor());
       pedido.setDescricao(pedidoAlterado.getDescricao());
       pedido.setAlturaCava(pedidoAlterado.getAlturaCava());
       pedido.setBusto(pedidoAlterado.getBusto());
       pedido.setCintura(pedidoAlterado.getCintura());
       pedido.setQuadril(pedidoAlterado.getQuadril());
       pedido.setComprimentoManga(pedidoAlterado.getComprimentoManga());
       pedido.setLargura(pedidoAlterado.getLargura());
       pedido.setComprimentoSaia(pedidoAlterado.getComprimentoSaia());
         
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

    Pedido pedido = repository.findById(id).orElse(null);  
    if (pedido != null) {
        pedido.setHabilitado(Boolean.FALSE);
        pedido.setVersao(pedido.getVersao() + 1);

        repository.save(pedido);

        emailService.enviarComprovanteFinal(pedido);

        repository.delete(pedido);
    } else {
        System.out.println("Pedido não encontrado para o ID: " + id);
    }
}
}
