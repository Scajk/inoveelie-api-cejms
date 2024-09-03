package br.com.ifpe.inoveelie.modelo.pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifpe.inoveelie.modelo.usuario.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    List<Pedido> findByUsuario(Usuario usuario);
}
