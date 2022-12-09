package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.dwarf.carta.Carta;

@DataJpaTest
public class TableroRepositoryTest {

    @Autowired
    TableroRepository tableroRepository;

    @Test
    public void testFindAllCartas() {
        List<Carta> cartas = tableroRepository.findAllCartas();
        assertNotNull(cartas);
        assertFalse(cartas.isEmpty());
        assertEquals(63, cartas.size());
    }

    @Test
    public void testFindCartaById() {
        Integer id = 1;
        Carta carta = tableroRepository.findCartaById(id);
        assertNotNull(carta);
    }

    @Test
    public void testFindCartaByPosicion() {
        Integer id = 1;
        List <Carta> cartas = tableroRepository.findByPosicion(id);
        assertNotNull(cartas);
        assertEquals(6, cartas.size());
    }


    /* 
    private Tablero tableroPrueba;

    @BeforeEach
    public void setup() {
        tableroPrueba = new Tablero();
        tableroPrueba.setId(1);
        tableroPrueba.setName("Tablero de prueba");
        tableroPrueba.setRonda(1);
        tableroPrueba.setJugadores(new ArrayList<>());
        tableroPrueba.setMazos(new ArrayList<>());
        given(this.tableroService.findById(1)).willReturn(tableroPrueba);
    }


    @Test
    public void testFindById() {
        // Tablero t = new Tablero();
        // t.setId(1);
        // Integer id = 1;
        Tablero tablero = this.tableroService.findById(1);
        assertNotNull(tablero);
    }
    */
}
