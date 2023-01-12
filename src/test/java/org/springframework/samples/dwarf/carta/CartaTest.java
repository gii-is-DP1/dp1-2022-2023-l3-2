package org.springframework.samples.dwarf.carta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.tablero.ChatLine;
import org.springframework.samples.dwarf.tablero.ChatLineService;
import org.springframework.samples.dwarf.tablero.Enano;
import org.springframework.samples.dwarf.tablero.Mazo;
import org.springframework.samples.dwarf.tablero.MazoService;
import org.springframework.samples.dwarf.tablero.Tablero;
import org.springframework.samples.dwarf.tablero.TableroService;
import org.springframework.samples.dwarf.user.Authorities;
import org.springframework.samples.dwarf.user.AuthoritiesService;
import org.springframework.samples.dwarf.user.EstadisticaService;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;

@DataJpaTest
public class CartaTest {

    @Test
    public void shouldForja() {
        CartaForja cartaForja = new CartaForja();
        Carta carta = new Carta();
        carta.setEntrada("hierro");
        carta.setCantidadentrada(3);
        carta.setCantidaddevuelve(2);
        carta.setDevuelve("acero");

        Enano e = new Enano();
        List<Mazo> mazos = new ArrayList<>();
        mazos.add(new Mazo());
        e.setMazo(mazos.get(0));
        List<Carta> cartas = new ArrayList<>();
        cartas.add(carta);
        e.getMazo().setCartas(cartas);
        Tablero tableroPrueba = new Tablero();
        Jugador alegarsan = new Jugador();
        alegarsan.setHierro(3);
        alegarsan.setAcero(0);
        cartaForja.accion4(tableroPrueba, alegarsan, e);
        assertEquals(alegarsan.getAcero(), 2);
    }

    @Test
    public void shouldDefensa() {
        CartaDefensa cartaDefensa = new CartaDefensa();
        Carta carta = new Carta();
        carta.setEntrada("hierro");
        carta.setCantidadentrada(3);
        carta.setCantidaddevuelve(2);
        carta.setDevuelve("acero");

        Enano e = new Enano();
        List<Mazo> mazos = new ArrayList<>();
        mazos.add(new Mazo());
        e.setMazo(mazos.get(0));
        List<Carta> cartas = new ArrayList<>();
        cartas.add(carta);
        e.getMazo().setCartas(cartas);
        Tablero tableroPrueba = new Tablero();
        Jugador alegarsan = new Jugador();
        alegarsan.setMedalla(0);
        alegarsan.setHierro(3);
        alegarsan.setAcero(0);
        cartaDefensa.accion2(tableroPrueba, alegarsan, e);
        assertEquals(alegarsan.getMedalla(), 1);
    }

    @Test
    public void shouldEctraccion() {
        CartaExtraccion cartaExtraccion = new CartaExtraccion();
        Carta carta = new Carta();

        carta.setCantidaddevuelve(3);
        carta.setDevuelve("hierro");

        Enano e = new Enano();
        List<Mazo> mazos = new ArrayList<>();
        mazos.add(new Mazo());
        e.setMazo(mazos.get(0));
        List<Carta> cartas = new ArrayList<>();
        cartas.add(carta);
        e.getMazo().setCartas(cartas);
        Tablero tableroPrueba = new Tablero();
        Jugador alegarsan = new Jugador();
        alegarsan.setMedalla(0);
        alegarsan.setHierro(0);
        alegarsan.setAcero(0);
        cartaExtraccion.accion3(tableroPrueba, alegarsan, e);
        assertEquals(alegarsan.getHierro(), 3);
    }

    @Test
    public void shouldEspecial() {
        CartaEspecial cartaExtraccion = new CartaEspecial();
        CartaEspecial carta = new CartaEspecial();
        carta.setId(59);
        carta.setCantidaddevuelve(3);
        carta.setDevuelve("hierro");

        Enano e = new Enano();
        List<Mazo> mazos = new ArrayList<>();
        mazos.add(new Mazo());
        e.setMazo(mazos.get(0));
        List<Carta> cartas = new ArrayList<>();
        cartas.add(carta);
        e.getMazo().setCartas(cartas);
        Tablero tableroPrueba = new Tablero();
        tableroPrueba.setMazos(mazos);
        Jugador alegarsan = new Jugador();
        alegarsan.setMedalla(0);
        alegarsan.setHierro(3);
        alegarsan.setAcero(1);
        alegarsan.setOro(1);
        alegarsan.setObjeto(0);
        cartaExtraccion.accion5(tableroPrueba, alegarsan, e);
        assertEquals(alegarsan.getObjeto(), 1);
    }
}
