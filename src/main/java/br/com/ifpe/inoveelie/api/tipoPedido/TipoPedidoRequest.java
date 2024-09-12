package br.com.ifpe.inoveelie.api.tipoPedido;

import br.com.ifpe.inoveelie.modelo.tipoPedido.TipoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoPedidoRequest {

    private String descricao;

    public TipoPedido build() {
        return TipoPedido.builder()
            .descricao(descricao)
            .habilitado(Boolean.TRUE) // Define o valor padrão para 'habilitado'
            .versao(1L)              // Define o valor padrão para 'versao'
            .dataCriacao(LocalDate.now()) // Define o valor padrão para 'dataCriacao'
            .build();
    }
}
