package br.com.ifpe.inoveelie.api.pedido;

import java.time.LocalDate;
import br.com.ifpe.inoveelie.modelo.pedido.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PedidoRequest {
    
   private Long idMaterial;

   private Long idStatusPedido;

   private Long idCliente;

   private Long idUsuario;

   private Long idMedida;

   private LocalDate dataEntrega;

   private String tipoPedido;

   private Double qtd;

   private Float valor;

   public Pedido build() {

    return Pedido.builder()
        .tipoPedido(tipoPedido)
        .qtd(qtd)
        .valor(valor)
        .dataEntrega(dataEntrega)
        .build();
}
}
