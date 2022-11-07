package org.springframework.samples.dwarf.tablero;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.samples.dwarf.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Carta extends BaseEntity {
    String descripcion;
    String accion;

    @ManyToOne
    @JoinColumn(name = "tipo", referencedColumnName = "name")
    TipoCarta tipo;

    boolean enBaraja;
    Integer posicion;
}
