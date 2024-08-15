package br.com.ifpe.inoveelie.api.itensPedido;

import br.com.ifpe.inoveelie.modelo.itensPedido.ItensPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ItensPedidoRequest {
    
   private Long idMaterial;

   private Long idPedido;

   private Double qtd;

   private Float valor;

   public ItensPedido build() {

    return ItensPedido.builder()
        .qtd(qtd)
        .valor(valor)
        .build();
   }
}
