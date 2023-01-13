package org.springframework.samples.dwarf.carta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.tablero.Tablero;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "defensa")
public class CartaDefensa extends Carta {
    @Override
    public void accion2(Tablero tablero, Jugador j, Enano e) {
        j.setMedalla(j.getMedalla() + 1);
    }
}
