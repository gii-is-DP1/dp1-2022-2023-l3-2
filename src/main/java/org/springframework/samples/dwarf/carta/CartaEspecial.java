package org.springframework.samples.dwarf.carta;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.tablero.Mazo;
import org.springframework.samples.dwarf.tablero.Tablero;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "especial")
public class CartaEspecial extends Carta {

    @Override
    public String accion5(Tablero tablero, Jugador j) {
        List<Integer> idCartasAyuda = List.of(55, 56, 57, 58, 59, 60, 61, 62, 63);
        Carta primera = this;
        if (idCartasAyuda.contains(primera.getId())) {
            if (primera.getId() == 55) {
                tablero.setDefensaTotal(true);
            }
            if (primera.getId() == 56) {

                for (int i = 0; i < 9; i++) {
                    if (tablero.getMazos().get(i).getCartas().size() >= 2) {
                        Carta priemraCarta = tablero.getMazos().get(i).getFirstCarta();
                        tablero.getMazos().get(i).getCartas().remove(0);
                        tablero.getMazos().get(tablero.getMazos().size() - 1).getCartas().add(i, priemraCarta);
                    }
                }
            }
            if (primera.getId() == 57) {

                if (j.getObjeto() >= 1) {
                    j.setObjeto(j.getObjeto() - 1);
                    tablero.getMazos().stream().filter(mazo -> mazo.getFirstCarta().equals(primera)).toList().get(0)
                            .getCartas().remove(primera);
                    return "redirect:/partida/" + tablero.getId() + "/eleccion-materiales?username="
                            + j.getUser().getUsername();
                    // Elegir los elementos
                }
                return null;
            }
            if (primera.getId() == 58) {

                return "redirect:/partida/" + tablero.getId() + "/eleccion-cartas?username="
                        + j.getUser().getUsername();
            }
            if (primera.getId() == 59) {

                if (j.getAcero() >= 1 && j.getOro() >= 1 && j.getHierro() >= 1) {
                    if (j.getHierro() - 1 >= 2) {
                        j.setAcero(j.getAcero() - 1);
                        j.setHierro(j.getHierro() - 3);
                        j.setOro(j.getOro() - 1);
                        j.setObjeto(j.getObjeto() + 1);
                    }
                    else if (j.getAcero() - 1 >= 2) {
                        j.setAcero(j.getAcero() - 3);
                        j.setOro(j.getOro() - 1);
                        j.setHierro(j.getHierro() - 1);
                        j.setObjeto(j.getObjeto() + 1);
                    }
                    else if (j.getOro() - 1 >= 2) {
                        j.setAcero(j.getAcero() - 1);
                        j.setOro(j.getOro() - 3);
                        j.setHierro(j.getHierro() - 1);
                        j.setObjeto(j.getObjeto() + 1);
                    }

                }
            }
            if (primera.getId() == 60) {
                if (j.getObjeto() >= 1) {
                    j.setObjeto(j.getObjeto() - 1);
                    tablero.getMazos().stream().filter(mazo -> mazo.getFirstCarta().equals(primera)).toList().get(0)
                            .getCartas().remove(primera);
                    return "redirect:/partida/" + tablero.getId() + "/eleccion-materiales?username="
                            + j.getUser().getUsername();
                    // Elegir los elementos
                }
                return null;
            }
            if (primera.getId() == 61) {
                return "redirect:/partida/" + tablero.getId() + "/eleccion-cartas?username="
                        + j.getUser().getUsername();
            }
            if (primera.getId() == 62) {
                // situa la primera carta de los mazos principales en el principio del mazo
                // REVISAR
                for (int i = 0; i < 9; i++) {
                    Carta priemraCarta = tablero.getMazos().get(i).getFirstCarta();
                    Mazo mazoActual = tablero.getMazos().get(i);
                    mazoActual.getCartas().add(mazoActual.getCartas().size() - 1, priemraCarta);
                    mazoActual.getCartas().remove(0);
                }
            }
            if (primera.getId() == 63) {
                // Por cada ubicacion Baraja las cartas del mazo REVISAR
                for (int i = 0; i < 9; i++) {
                    int DESDE = 0;
                    int HASTA = tablero.getMazos().get(i).getCartas().size() - 1;

                    int indexCarta = (int) (Math.random() * (HASTA - DESDE + 1) + DESDE);
                    Carta carta = tablero.getMazos().get(i).getCartas().get(indexCarta);
                    tablero.getMazos().stream().filter(mazo -> mazo.getPosicion() == carta.getPosicion()).toList()
                            .get(0)
                            .getCartas()
                            .add(0, carta);
                    tablero.getMazos().get(i).getCartas().remove(carta);

                }
            }

            tablero.getMazos().stream().filter(mazo -> mazo.getFirstCarta().equals(primera)).toList().get(0)
                    .getCartas().remove(primera);

        }
        return null;
    }

    public String accion5(Tablero tablero, Jugador j, Enano e) {
        List<Integer> idCartasAyuda = List.of(55, 56, 57, 58, 59, 60, 61, 62, 63);
        Carta primera = e.getMazo().getFirstCarta();
        if (idCartasAyuda.contains(primera.getId())) {
            if (primera.getId() == 55) {
                tablero.setDefensaTotal(true);
            }
            if (primera.getId() == 56) {

                for (int i = 0; i < 9; i++) {
                    if (tablero.getMazos().get(i).getCartas().size() >= 2) {
                        Carta priemraCarta = tablero.getMazos().get(i).getFirstCarta();
                        tablero.getMazos().get(i).getCartas().remove(0);
                        tablero.getMazos().get(tablero.getMazos().size() - 1).getCartas().add(i, priemraCarta);
                    }
                }
            }
            if (primera.getId() == 57) {

                if (j.getObjeto() >= 1) {
                    j.setObjeto(j.getObjeto() - 1);
                    tablero.getMazos().stream().filter(mazo -> mazo.getFirstCarta().equals(primera)).toList().get(0)
                            .getCartas().remove(primera);
                    return "redirect:/partida/" + tablero.getId() + "/eleccion-materiales?username="
                            + j.getUser().getUsername();
                    // Elegir los elementos
                }
                return null;
            }
            if (primera.getId() == 58) {

                return "redirect:/partida/" + tablero.getId() + "/eleccion-cartas?username="
                        + j.getUser().getUsername();
            }
            if (primera.getId() == 59) {

                if (j.getAcero() >= 1 && j.getOro() >= 1 && j.getHierro() >= 1) {
                    if (j.getHierro() - 1 >= 2) {
                        j.setAcero(j.getAcero() - 1);
                        j.setHierro(j.getHierro() - 3);
                        j.setOro(j.getOro() - 1);
                        j.setObjeto(j.getObjeto() + 1);
                    } else if (j.getAcero() - 1 >= 2) {
                        j.setAcero(j.getAcero() - 3);
                        j.setOro(j.getOro() - 1);
                        j.setHierro(j.getHierro() - 1);
                        j.setObjeto(j.getObjeto() + 1);
                    } else if (j.getOro() - 1 >= 2) {
                        j.setAcero(j.getAcero() - 1);
                        j.setOro(j.getOro() - 3);
                        j.setHierro(j.getHierro() - 1);
                        j.setObjeto(j.getObjeto() + 1);
                    }

                }
            }
            if (primera.getId() == 60) {
                if (j.getObjeto() >= 1) {
                    j.setObjeto(j.getObjeto() - 1);
                    tablero.getMazos().stream().filter(mazo -> mazo.getFirstCarta().equals(primera)).toList().get(0)
                            .getCartas().remove(primera);
                    return "redirect:/partida/" + tablero.getId() + "/eleccion-materiales?username="
                            + j.getUser().getUsername();
                    // Elegir los elementos
                }
                return null;
            }
            if (primera.getId() == 61) {
                return "redirect:/partida/" + tablero.getId() + "/eleccion-cartas?username="
                        + j.getUser().getUsername();
            }
            if (primera.getId() == 62) {
                // situa la primera carta de los mazos principales en el principio del mazo
                // REVISAR
                for (int i = 0; i < 9; i++) {
                    Carta priemraCarta = tablero.getMazos().get(i).getFirstCarta();
                    Mazo mazoActual = tablero.getMazos().get(i);
                    mazoActual.getCartas().add(mazoActual.getCartas().size() - 1, priemraCarta);
                    mazoActual.getCartas().remove(0);
                }
            }
            if (primera.getId() == 63) {
                // Por cada ubicacion Baraja las cartas del mazo REVISAR
                for (int i = 0; i < 9; i++) {
                    int DESDE = 0;
                    int HASTA = tablero.getMazos().get(i).getCartas().size() - 1;

                    int indexCarta = (int) (Math.random() * (HASTA - DESDE + 1) + DESDE);
                    Carta carta = tablero.getMazos().get(i).getCartas().get(indexCarta);
                    tablero.getMazos().stream().filter(mazo -> mazo.getPosicion() == carta.getPosicion()).toList()
                            .get(0)
                            .getCartas()
                            .add(0, carta);
                    tablero.getMazos().get(i).getCartas().remove(carta);

                }
            }

            tablero.getMazos().stream().filter(mazo -> mazo.getFirstCarta().equals(primera)).toList().get(0)
                    .getCartas().remove(primera);

        }
        return null;
    }

}
