package br.com.ifpe.inoveelie.modelo.pedido;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ifpe.inoveelie.modelo.usuario.Usuario;
import br.com.ifpe.inoveelie.modelo.usuario.UsuarioService;
import jakarta.transaction.Transactional;

@Service
public class PedidoService {

   @Autowired
   private PedidoRepository repository;

   @Autowired
   private UsuarioService usuarioService;

   @Transactional
   public Pedido save(Pedido pedido) {
       Usuario usuarioAtual = usuarioService.obterUsuarioAtual();
       pedido.setUsuario(usuarioAtual);
       pedido.setHabilitado(Boolean.TRUE);
       pedido.setVersao(1L);
       pedido.setDataCriacao(LocalDate.now());
       return repository.save(pedido);
   }

   @Transactional
   public Pedido update(Long id, Pedido pedidoAlterado) {
       Usuario usuarioAtual = usuarioService.obterUsuarioAtual();
       Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

       if (!pedido.getUsuario().equals(usuarioAtual)) {
           throw new RuntimeException("Você não tem permissão para modificar este pedido.");
       }

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
       pedido.setUsuario(usuarioAtual);

       pedido.setVersao(pedido.getVersao() + 1);
       return repository.save(pedido);
   }

   @Transactional
   public void updateStatus(Long id, String status) {
       Usuario usuarioAtual = usuarioService.obterUsuarioAtual();
       Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

       if (!pedido.getUsuario().equals(usuarioAtual)) {
           throw new RuntimeException("Você não tem permissão para modificar o status deste pedido.");
       }

       pedido.setStatus(status);
       pedido.setVersao(pedido.getVersao() + 1);
       repository.save(pedido);
   }

   public List<Pedido> listarTodos() {
       Usuario usuarioAtual = usuarioService.obterUsuarioAtual();
       return repository.findByUsuario(usuarioAtual);
   }

   public Pedido obterPorID(Long id) {
       Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
       Usuario usuarioAtual = usuarioService.obterUsuarioAtual();

       if (!pedido.getUsuario().equals(usuarioAtual)) {
           throw new RuntimeException("Você não tem permissão para visualizar este pedido.");
       }

       return pedido;
   }

   @Transactional
   public void delete(Long id) {
       Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
       Usuario usuarioAtual = usuarioService.obterUsuarioAtual();

       if (!pedido.getUsuario().equals(usuarioAtual)) {
           throw new RuntimeException("Você não tem permissão para apagar este pedido.");
       }

       pedido.setHabilitado(Boolean.FALSE);
       pedido.setVersao(pedido.getVersao() + 1);
       repository.save(pedido);
   }
}