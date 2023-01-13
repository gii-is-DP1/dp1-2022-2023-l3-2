package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.samples.dwarf.user.UserService;

import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TableroServiceTest {

    @Autowired
    protected TableroService tableroService;

    @Autowired
    protected JugadorService jugadorService;

    @Autowired
    protected UserService userService;

    Tablero tablero;
    Mazo mazo;


    @Test
    public void shouldFindAll() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
        tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                        "alegarsan11", "rafgargal", null, jugadores);
        tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                        "alegarsan11", "rafgargal", null, jugadores);
        tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                        "alegarsan11", "rafgargal", null, jugadores);
        List<Tablero> tabs = tableroService.findAll();
        assertThat(tabs.size()).isEqualTo(3);
    }


    @Test
    public void shouldFindAllFinished() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
        Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                        "alegarsan11", "rafgargal", null, jugadores);
        Tablero ta2 = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                        "alegarsan11", "rafgargal", null, jugadores);
        Tablero ta3 = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                        "alegarsan11", "rafgargal", null, jugadores);
        ta.setFinishedAt(new Date());
        ta2.setFinishedAt(new Date());
        ta3.setFinishedAt(new Date());

        assertThat(tableroService.findAllFinished().size()).isEqualTo(3);

    }

    @Test
    public void shouldFindAllNotFinished() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            Tablero ta2 = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            Tablero ta3 = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);

            assertThat(tableroService.findAllNotFinished().size()).isEqualTo(3);

    }

    @Test
    public void shouldFindById() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            Tablero tab = tableroService.findById(ta.getId());
        assertThat(tab.getName()).isEqualTo("Este es el tablero de prueba");
    }

    @Test
    public void saveTestSucessful() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
        assertEquals(tableroService.findAll().size(), 1);

    }

    @Test
    public void saveTestUnsucessful() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
        assertEquals(tableroService.findAll().size(), 1);
    }

    @Test
    public void shouldFindAllCartas() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
    }

    @Test
    public void shouldFindCartasById() {
        Carta carta = tableroService.findCartaById(1);
        assertEquals(List.of(carta).size(), 1);
        assertEquals(tableroService.findCartaById(1).getId(), 1);
        assertThat(carta.getImagen()).isEqualTo("/resources/images/Dimensionadas/001.png");
        assertThat(carta.getDevuelve()).isEqualTo("hierro");
        assertThat(carta.getCantidaddevuelve()).isEqualTo(3);
    }

    @Test
    public void shouldFindCartasByPosicion() {
        List<Carta> cartas = tableroService.findByPosicion(1);
        // Existen 6 cartas en la base de datos con la posicion 1
        assertEquals(cartas.size(), 6);
    }


    @Test
    public void shouldFindLastNGame() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            ta.setTerminada(true);
            List<Tablero> tabs = tableroService.findLastNGames(1);
            assertThat(tabs.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindLastNGameByUser() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            ta.setTerminada(true);
            List<Tablero> tabs = tableroService.findLastNGamesByUser(userService.findUserByString("alegarsan11").get(0),
                            1);
            assertThat(tabs.size()).isEqualTo(1);
    }

    @Test
    public void shouldSerEspectador() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            ta.setTerminada(true);
            Boolean tabs = tableroService.puedoSerEspectador(ta, userService.findUserByString("jualeomad").get(0));
            assertThat(tabs).isEqualTo(false);
    }

    @Test
    public void sacaycolocacartas() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            ta.setTerminada(true);
            tableroService.sacarYColocarCartasDeBaraja(ta);
            assertTrue(ta.getMazos().get(12).getCartas().size() == 43
                            || ta.getMazos().get(12).getCartas().size() == 42);
    }

    @Test
    public void descolocarEnanos() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            ta.setTerminada(true);
            tableroService.descolocarEnanos(ta);

            assertTrue(ta.getJugadores().stream().allMatch(j -> j.getEnano().get(0).getPosicion() == 12));
    }

    @Test
    public void calcularPosiciones() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            ta.setTerminada(true);
            ta.getJugadores().get(0).setObjeto(4);
            tableroService.calcularPosicionesFinales(ta);

            assertEquals(jugadorService.findJugadorUser("alegarsan11").get(1).getPosicionFinal(), 1);
    }

    @Test
    public void shouldDelete() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            Tablero ta2 = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            Tablero ta3 = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);

            tableroService.deleteById(ta.getId());
            List<Tablero> tabs = tableroService.findAll();
            assertThat(tabs.size()).isEqualTo(2);
    }

    @Test
    public void shouldFindByUser() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            List<Tablero> t = tableroService.findByUser(userService.findUser("alegarsan11").get());
            assertThat(t.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindByUserEnCurso() {
            List<Jugador> jugadores = jugadorService.creacionLista("alegarsan11", "rafgargal", null);
            Tablero ta = tableroService.saveTableroFromProcess("Este es el tablero de prueba",
                            "alegarsan11", "rafgargal", null, jugadores);
            List<Tablero> t = tableroService.findEnCursoByUser(userService.findUser("alegarsan11").get());
            assertThat(t.size()).isEqualTo(1);
    }

}
