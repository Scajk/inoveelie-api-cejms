package br.com.ifpe.inoveelie.modelo.tipoPedido;

import java.time.LocalDate;

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
@Table(name = "TipoPedido")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoPedido extends EntidadeAuditavel {

    @Column(nullable = false, unique = true)
    private String descricao;

    @Column(nullable = false)
    private Boolean habilitado;

    @Column(nullable = false)
    private Long versao;

    @Column(nullable = false)
    private LocalDate dataCriacao;

}
