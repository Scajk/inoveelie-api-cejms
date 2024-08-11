package br.com.ifpe.inoveelie.modelo.statusPedido;

import org.hibernate.annotations.SQLRestriction;
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
@Table(name = "StatusPedido")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StatusPedido extends EntidadeAuditavel{
    
   @Column
   private String emAndamento;

   @Column
   private String cancelado;

   @Column
   private String finalizado;
}
