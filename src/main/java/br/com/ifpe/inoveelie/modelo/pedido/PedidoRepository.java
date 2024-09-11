package br.com.ifpe.inoveelie.modelo.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ifpe.inoveelie.modelo.usuario.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    /**
     * Encontra todos os pedidos associados a um usuário específico.
     * 
     * @param usuario O usuário cujos pedidos estão sendo buscados.
     * @return Uma lista de pedidos associados ao usuário.
     */
    List<Pedido> findByUsuario(Usuario usuario);
}
