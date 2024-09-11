package br.com.ifpe.inoveelie.modelo.statusPedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import br.com.ifpe.inoveelie.util.entity.EntidadeAuditavel;

@Entity
@Table(name = "StatusPedido")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusPedido extends EntidadeAuditavel {

    @Column
    private String nome;

    @Column
    private String emAndamento;

    @Column
    private String cancelado;

    @Column
    private String finalizado;

    @Column
    private Boolean habilitado;

    @Column
    private Long versao;

    @Column
    private LocalDate dataCriacao;
}
