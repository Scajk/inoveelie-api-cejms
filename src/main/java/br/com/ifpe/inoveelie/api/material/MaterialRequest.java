package br.com.ifpe.inoveelie.api.material;

import br.com.ifpe.inoveelie.modelo.material.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MaterialRequest {
    
   private Integer idLogin;
   private String nome;
   private String descricao;
   private String quantidade;
   private String img;

   public Material build() {

       return Material.builder()
        .idLogin(idLogin)
        .nome(nome)
        .descricao(descricao)
        .quantidade(quantidade)
        .img(img)
        .build();
}
}