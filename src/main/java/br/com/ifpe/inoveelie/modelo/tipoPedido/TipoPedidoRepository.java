package br.com.ifpe.inoveelie.modelo.tipoPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TipoPedidoRepository extends JpaRepository<TipoPedido, Long> {

    Optional<TipoPedido> findByDescricao(String descricao);
}
