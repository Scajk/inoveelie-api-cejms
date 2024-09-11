package br.com.ifpe.inoveelie.modelo.pedido;

import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.inoveelie.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.ifpe.inoveelie.modelo.tipoPedido.TipoPedido;
import br.com.ifpe.inoveelie.modelo.usuario.Usuario;

@Entity
@Table(name = "Pedido")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends EntidadeAuditavel {
   
   @ManyToOne
   private TipoPedido tipo;

   @Column
   private String nomeCliente;

   @Column
   private String numeroCliente;

   @Column
   private LocalDate dataEntrega;

   @Column
   private Float valor;

   @Column
   private String descricao;

   @Column
   private Double alturaCava;

   @Column
   private Double busto;

   @Column
   private Double cintura;

   @Column
   private Double quadril;

   @Column
   private Double comprimentoManga;

   @Column
   private Double largura;

   @Column
   private Double comprimentoSaia;

   @ManyToOne
   private Usuario usuario;

   @Column
   private String status;
}
