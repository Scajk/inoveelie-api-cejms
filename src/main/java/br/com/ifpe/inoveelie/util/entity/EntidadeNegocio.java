package br.com.ifpe.inoveelie.util.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@MappedSuperclass

public abstract class EntidadeNegocio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @Column
    private Boolean habilitado;
    
}
