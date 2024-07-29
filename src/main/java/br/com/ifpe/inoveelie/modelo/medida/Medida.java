package br.com.ifpe.inoveelie.modelo.medida;

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
@Table(name = "Medida")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Medida extends EntidadeAuditavel{

   @Column
   private Double busto;

   @Column
   private Double cintura;

   @Column
   private Double quadril;

   @Column
   private Double alturaManga;

   @Column
   private Double alturaCava;

   @Column
   private Double largura;

   @Column
   private Double comprimentoSaia;
}
