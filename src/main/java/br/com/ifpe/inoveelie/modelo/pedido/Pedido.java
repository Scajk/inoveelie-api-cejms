package br.com.ifpe.inoveelie.modelo.pedido;

import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.inoveelie.modelo.material.Material;
import br.com.ifpe.inoveelie.modelo.cliente.Cliente;
import br.com.ifpe.inoveelie.modelo.usuario.Usuario;
import br.com.ifpe.inoveelie.modelo.medida.Medida;
import br.com.ifpe.inoveelie.modelo.statusPedido.StatusPedido;
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
@Table(name = "Pedido")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Pedido extends EntidadeAuditavel{
    
   @ManyToOne
   private Cliente clientes;

   @ManyToOne
   private Usuario usuarios;

   @ManyToOne
   private Material materiais;

   @ManyToOne
   private Medida medidas;

   @ManyToOne
   private StatusPedido statusPedidos;

   @Column
   private Double qtd;

   @Column
   private LocalDate dataEntrega;

   @Column
   private String tipoPedido;

   @Column
   private Float valor;

}
