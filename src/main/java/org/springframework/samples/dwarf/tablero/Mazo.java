package org.springframework.samples.dwarf.tablero;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.samples.dwarf.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter

public class Mazo extends NamedEntity {

    private Integer posicion;

    @ManyToMany
    @JoinTable(name = "cartas_mazo", joinColumns = @JoinColumn(name = "carta_id"))
    private List<Carta> cartas;

    public Carta getFirstCarta() {
        return cartas.get(0);
    }
}
