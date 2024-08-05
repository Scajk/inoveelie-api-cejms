package br.com.ifpe.inoveelie.api.cliente;

import br.com.ifpe.inoveelie.modelo.cliente.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ClienteRequest {
    
   private String nomeCliente;
   private String numeroCliente;

   public Cliente build() {

       return Cliente.builder()
        .nomeCliente(nomeCliente)
        .numeroCliente(numeroCliente)
        .build();
}
}
