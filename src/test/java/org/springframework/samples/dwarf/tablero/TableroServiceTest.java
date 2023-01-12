package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.carta.TipoCarta;
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

    @BeforeEach
    public void config(){
        mazo=new Mazo();
        tablero=new Tablero();
        tablero.setName("Este es el tablero de prueba");
        Jugador ale = jugadorService.findJugadorUser("alegarsan11").get(0);
        List<Jugador> jugadors = List.of(ale);
        tablero.setFinishedAt(new Date());
        tablero.setTerminada(true);
        tablero.setJugadores(jugadors);
        tableroService.saveTablero(tablero);
    }

    @Test
    public void shouldFindAll() {
        List<Tablero> tabs = tableroService.findAll();
        assertThat(tabs.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindAllFinished() {
        List<Tablero> tabs = tableroService.findAllFinished();
        assertThat(tabs.size()).isEqualTo(1);

        Tablero tab = tabs.get(0);
        assertThat(tab.isTerminada()).isEqualTo(true);
    }

    @Test
    public void shouldFindById() {
        Tablero tab = tableroService.findById(1);
        assertThat(tab.getName()).isEqualTo("Este es el tablero de prueba");
        assertThat(tab.isTerminada()).isEqualTo(true);
    }

    /* @Test    
    public void saveAndDeleteTestSucessful() {
        tableroService.saveTablero(tablero);
        assertEquals(tableroService.findAll().size(), 1);
        assertEquals(tableroService.findById(2).getName(), "Este es el tablero de prueba");
        assertEquals(
                tableroService.findLastNGamesByUser(jugadorService.findJugadorUser("alegarsan11").get(0).getUser(), 1)
                        .size(),
                1);
        tableroService.deleteById(1);
        assertEquals(tableroService.findAll(), List.of());
    } */

    /* @Test
    public void saveTestUnsucessful() {
        Tablero tablero2 = new Tablero();
        tablero2.setName("");
        tablero2.setId(2);
        assertEquals(tableroService.findAll().size(), 1);
    } */

    @Test
    public void shouldFindAllCartas() {
        List<Carta> cartas = tableroService.findAllCartas();
        assertEquals(cartas.size(), 66);
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
    public void shouldDeleteById() {
        tableroService.deleteById(1);
        assertThat(tableroService.findAll().size()).isEqualTo(1);
    }

    /* @Test
    public void shouldFindByUser() {
        List<Tablero> tabs = tableroService.findByUser(user1);
        assertThat(tabs.size()).isEqualTo(1);
    } */

    /* @Test
    public void shouldFindEnCursoByUser() {
        List<Tablero> tabs = tableroService.findEnCursoByUser(user1);
        assertThat(tabs.size()).isEqualTo(0);
    } */

    /* @Test
    public void shouldFindLastNGamesByUser() {
        List<Tablero> tabs = tableroService.findLastNGamesByUser(user1, 1);
        assertThat(tabs).isEqualTo(1);
    } */

    @Test
    public void shouldFindLastNGame() {
        List<Tablero> tabs = tableroService.findLastNGames(1);
        assertThat(tabs.size()).isEqualTo(1);
    }

}
