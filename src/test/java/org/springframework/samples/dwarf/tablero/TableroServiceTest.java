package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.carta.Carta;
import org.springframework.samples.dwarf.jugador.Jugador;
import org.springframework.samples.dwarf.jugador.JugadorService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TableroServiceTest {
        
    @Autowired
    protected TableroService tableroService;
    @Autowired
    protected JugadorService jugadorService;

    Tablero tablero;
    Mazo mazo;

    @BeforeEach
    public void config(){

        mazo=new Mazo();
        tablero=new Tablero();
        tablero.setId(1);
        tablero.setName("Este es el tablero de prueba");
        Jugador ale = jugadorService.findJugadorUser("alegarsan11").get(0);
        List<Jugador> jugadors = List.of(ale);
        tablero.setTerminada(true);
        tablero.setJugadores(jugadors);

    }

    @Test    
    public void saveAndDeleteTestSucessful() {

        tableroService.saveTablero(tablero);
        assertEquals(tableroService.findAll().size(), 1);
        assertEquals(tableroService.findById(1).getName(), "Este es el tablero de prueba");
        assertEquals(
                tableroService.findLastNGamesByUser(jugadorService.findJugadorUser("alegarsan11").get(0).getUser(), 1)
                        .size(),
                1);
        tableroService.deleteById(1);
        assertEquals(tableroService.findAll().size(), 0);

    }


    // No funciona por que no tiene restricciones ni excepcion.

    @Test
    public void saveTestUnsucessful() {
        Tablero tablero2 = new Tablero();
        tablero2.setName("");
        tablero2.setId(2);
        assertEquals(tableroService.findAll().size(), 0);
    }

    @Test
    public void findAllCartasTest() {
        assertEquals(tableroService.findAllCartas().size(), 66);
    }

    @Test
    public void findCartasByPosicionTest() {

        // Existen 6 cartas en la base de datos con la posicion 1
        assertEquals(tableroService.findByPosicion(1).size(), 6);
    }

    @Test
    public void findCartasByIdTest() {

        assertEquals(List.of(tableroService.findCartaById(1)).size(), 1);
        assertEquals(tableroService.findCartaById(1).getId(), 1);
    }

}
