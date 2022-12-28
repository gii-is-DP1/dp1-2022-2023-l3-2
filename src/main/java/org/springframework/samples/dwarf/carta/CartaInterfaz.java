package org.springframework.samples.dwarf.carta;

import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.tablero.Tablero;

public interface CartaInterfaz {
    public void accion1(Tablero tablero);

    public void accion2(Tablero tablero, Jugador j, Enano e);

    public void accion3(Tablero tablero, Jugador j, Enano e);

    public void accion4(Tablero tablero, Jugador j, Enano e);

    public void accion5(Tablero tablero, Jugador j, Enano e);

}
