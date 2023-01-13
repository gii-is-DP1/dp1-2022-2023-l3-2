package org.springframework.samples.dwarf.carta;

import java.util.Arrays;
import java.util.List;

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
@DiscriminatorValue(value = "forja")
public class CartaForja extends Carta {

    @Override
    public void accion4(Tablero tablero, Jugador j, Enano e) {
        Carta primera = e.getMazo().getFirstCarta();
        List<Integer> cartasForjaEspeciales = Arrays.asList(16, 25, 26, 45, 51);
        List<Integer> cartasQueNoModificanPrimerJugador = Arrays.asList(10, 15, 19, 21, 23, 29, 44, 54);

        if (j.isPrimerjugador() && !cartasQueNoModificanPrimerJugador.contains(primera.getId())) {
            j.setPrimerjugador(false);

            Integer indexJugador = tablero.getJugadores().indexOf(j);

            if (indexJugador == tablero.getNumJugadores() - 1) {
                tablero.getJugadores().get(0).setPrimerjugador(true);
            } else {
                tablero.getJugadores().get(indexJugador +
                        1).setPrimerjugador(true);
            }
        }

        // Condiciones especiales para las cartas de forja que piden materiales ditintos
        if (cartasForjaEspeciales.contains(primera.getId())) {

            if (primera.getId() == 16 && j.getAcero() >= 2 && j.getOro() >= 1) {
                j.setAcero(j.getAcero() - 2);
                j.setOro(j.getOro() - 1);
                j.setObjeto(j.getObjeto() + 1);
                return;
            }
            if (primera.getId() == 25 && j.getAcero() >= 1 && j.getOro() >= 1 && j.getHierro() >= 1) {
                j.setAcero(j.getAcero() - 1);
                j.setOro(j.getOro() - 1);
                j.setHierro(j.getHierro() - 1);
                j.setObjeto(j.getObjeto() + 1);
                return;
            }
            if (primera.getId() == 26 && j.getAcero() >= 1 && j.getOro() >= 2) {
                j.setAcero(j.getAcero() - 1);
                j.setOro(j.getOro() - 2);
                j.setObjeto(j.getObjeto() + 1);
                return;
            }
            if (primera.getId() == 45 && j.getAcero() >= 2 && j.getHierro() >= 1) {
                j.setHierro(j.getHierro() - 1);
                j.setAcero(j.getAcero() - 2);
                j.setObjeto(j.getObjeto() + 1);
                return;
            }
            if (primera.getId() == 51 && j.getAcero() >= 2 && j.getOro() >= 1) {
                j.setAcero(j.getAcero() - 2);
                j.setOro(j.getOro() - 1);
                j.setObjeto(j.getObjeto() + 1);
                return;
            }
        } else {

            if (primera.getEntrada().equals("hierro") && primera.getCantidadentrada() <= j.getHierro()) {
                j.setHierro(j.getHierro() - primera.getCantidadentrada());
                if (primera.getDevuelve().equals("objeto"))
                    j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
                else
                    j.setAcero(j.getAcero() + primera.getCantidaddevuelve());
            }
            if (primera.getEntrada().equals("oro") && primera.getCantidadentrada() <= j.getOro()) {
                j.setOro(j.getOro() - primera.getCantidadentrada());
                j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
            }
            if (primera.getEntrada().equals("medalla") && primera.getCantidadentrada() <= j.getMedalla()) {
                j.setMedalla(j.getMedalla() - primera.getCantidadentrada());
                j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
            }
            if (primera.getEntrada().equals("acero") && primera.getCantidadentrada() <= j.getAcero()) {
                j.setAcero(j.getAcero() - primera.getCantidadentrada());
                j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
            }
            if (primera.getEntrada().equals("objeto") && primera.getCantidadentrada() <= j.getObjeto()) {
                j.setObjeto(j.getObjeto() - primera.getCantidadentrada());
                j.setObjeto(j.getObjeto() + primera.getCantidaddevuelve());
            }
            e.setPosicion(12);
            e.setMazo(null);
        }
    }

}
