package br.com.ifpe.inoveelie.util.entity;

import java.time.LocalDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class EntidadeAuditavel extends EntidadeNegocio {
    
    @JsonIgnore
    @Version
    private Long versao;

    @JsonIgnore
    @CreatedDate
    private LocalDate dataCriacao;

    @JsonIgnore
    @LastModifiedDate
    private LocalDate dataUltimaModificacao;

    @JsonIgnore
    @Column
    private Long criadoPor; // Id do usuário que o criou

    @JsonIgnore
    @Column
    private Long ultimaModificacaoPor; // Id do usuário que fez a última alteração

}