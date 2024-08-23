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

   private String tipoPedido;

   private String nomeCliente;

   private String numeroCliente;

   private LocalDate dataEntrega;

   private Float valor;

   private String descricao;

   private Double alturaCava;

   private Double busto;

   private Double cintura;

   private Double quadril;

   private Double comprimentoManga;

   private Double largura;

   private Double comprimentoSaia;

   public Pedido build() {

    return Pedido.builder()
        .tipoPedido(tipoPedido)
        .nomeCliente(nomeCliente)
        .numeroCliente(numeroCliente)
        .dataEntrega(dataEntrega)
        .valor(valor)
        .descricao(descricao)
        .alturaCava(alturaCava)
        .busto(busto)
        .cintura(cintura)
        .quadril(quadril)
        .comprimentoManga(comprimentoManga)
        .largura(largura)
        .comprimentoSaia(comprimentoSaia)
        .build();
}
}
