package br.com.ifpe.inoveelie.api.medida;

import br.com.ifpe.inoveelie.modelo.medida.Medida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MedidaRequest {

   private Double busto;
   private Double cintura;
   private Double quadril;
   private Double alturaManga;
   private Double alturaCava;
   private Double largura;
   private Double comprimentoSaia;

   public Medida build() {

       return Medida.builder()
        .busto(busto)
        .cintura(cintura)
        .quadril(quadril)
        .alturaManga(alturaManga)
        .alturaCava(alturaCava)
        .largura(largura)
        .comprimentoSaia(comprimentoSaia)
        .build();
}
}
