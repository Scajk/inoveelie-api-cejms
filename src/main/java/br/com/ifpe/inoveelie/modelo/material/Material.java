package br.com.ifpe.inoveelie.modelo.material;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.DialectOverride.ColumnDefault;

import br.com.ifpe.inoveelie.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Material")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Material extends EntidadeAuditavel{
    
    @Column
    private Integer idLogin;

    @Column (length = 255)
    private String nome;
 
    @Column (length = 255)
    private String descricao;

    @Column (length = 32)
    private String quantidade;

    @Column
    private String img;

}
