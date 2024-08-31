package br.com.ifpe.inoveelie.modelo.pedido;

import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.inoveelie.modelo.cliente.Cliente;
import br.com.ifpe.inoveelie.modelo.tipoPedido.TipoPedido;
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

   @Column/* (nullable = false, unique = true) */
   private String email;

   private Long id;
   private Cliente cliente;

   private LocalDate dataCriacao;
   private Boolean habilitado;
   private Long versao;

   public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
