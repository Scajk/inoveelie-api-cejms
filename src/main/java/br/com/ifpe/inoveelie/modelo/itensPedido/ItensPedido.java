package br.com.ifpe.inoveelie.modelo.itensPedido;

import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.inoveelie.modelo.material.Material;
//import br.com.ifpe.inoveelie.modelo.pedido.Pedido;
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

@Entity
@Table(name = "ItensPedido")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItensPedido extends EntidadeAuditavel{
    
   @ManyToOne
   private Material materiais;

   @ManyToOne
   private Pedido pedidos;

   @Column
   private Double qtd;

   @Column
   private Float valor;
}
