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
@DiscriminatorValue(value = "extraccion")
public class CartaExtraccion extends Carta {

    @Override
    public void accion3(Tablero tablero, Jugador j, Enano e) {

        Carta primera = e.getMazo().getFirstCarta();

        if (primera.getDevuelve().equals("hierro")) {
            j.setHierro(j.getHierro() + primera.getCantidaddevuelve());
        }
        if (primera.getDevuelve().equals("oro")) {
            j.setOro(j.getOro() + primera.getCantidaddevuelve());
        }
        if (primera.getDevuelve().equals("medalla")) {
            j.setMedalla(j.getMedalla() + primera.getCantidaddevuelve());
        }
        if (primera.getDevuelve().equals("acero")) {
            j.setAcero(j.getAcero() + primera.getCantidaddevuelve());
        }
        if (primera.getDevuelve().equals("objeto")) {
            j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
        }
        e.setPosicion(12);
        e.setMazo(null);

    }
}
