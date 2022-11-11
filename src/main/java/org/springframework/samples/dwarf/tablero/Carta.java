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
    /*
     * String descripcion;
     * String accion;
     */

    String imagen;

    Integer posicion;

    @ManyToOne
    @JoinColumn(name="mazo_id")
    Mazo mazo;
    
    @ManyToOne 
    @JoinColumn(name = "tipo")
    TipoCarta tipo;
     
    String entrada;

    Integer cantidadentrada;

    String devuelve;

    Integer cantidaddevuelve;

}
