package br.com.ifpe.inoveelie.api.tipoPedido;

import br.com.ifpe.inoveelie.modelo.tipoPedido.TipoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TipoPedidoRequest {
    
   private String descricao;

   public TipoPedido build() {

       return TipoPedido.builder()
           .descricao(descricao)
           .build();
   }

}
