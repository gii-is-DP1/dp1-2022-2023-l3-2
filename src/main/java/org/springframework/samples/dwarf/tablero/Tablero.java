package org.springframework.samples.dwarf.tablero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.model.NamedEntity;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity
@Getter
@Setter
public class Tablero extends NamedEntity {

    Integer ronda;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "mazo_tablero", joinColumns = @JoinColumn(name = "tablero"))
    private List<Mazo> mazos;

    @ManyToMany
    @JoinTable(name = "jugadores_lobby", joinColumns = @JoinColumn(name = "jugador_id"))
    private List<Jugador> jugadores;

    public Integer getNumJugadores() {
        return jugadores.size();
    }
}
