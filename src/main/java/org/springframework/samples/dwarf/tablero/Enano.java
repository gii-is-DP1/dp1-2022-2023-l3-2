package org.springframework.samples.dwarf.tablero;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.samples.dwarf.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Enano extends NamedEntity{

    int posicion;
    
    @ManyToOne
    @JoinColumn(name="mazo_id")
    Mazo mazo;
}
