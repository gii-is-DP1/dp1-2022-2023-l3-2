package org.springframework.samples.dwarf.lobby;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Mensaje extends NamedEntity {
    
    private String Mensaje;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Jugador player;

}
