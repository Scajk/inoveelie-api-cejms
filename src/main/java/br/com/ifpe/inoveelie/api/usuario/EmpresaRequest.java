package br.com.ifpe.inoveelie.api.usuario;

import br.com.ifpe.inoveelie.modelo.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class EmpresaRequest {

    private String nomeEmpresa;
 
    private String cepEmpresa;
 
    public Empresa build() {
 
        return Empresa.builder()
                .nomeEmpresa(nomeEmpresa)
                .cepEmpresa(cepEmpresa)
                .build();
    }
 }
 
