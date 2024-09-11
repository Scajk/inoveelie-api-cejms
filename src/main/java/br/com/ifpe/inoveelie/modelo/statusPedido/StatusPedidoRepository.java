package br.com.ifpe.inoveelie.modelo.statusPedido;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusPedidoRepository extends JpaRepository<StatusPedido, Long>{

    Optional<StatusPedido> findByNome(String nome);
    
}
