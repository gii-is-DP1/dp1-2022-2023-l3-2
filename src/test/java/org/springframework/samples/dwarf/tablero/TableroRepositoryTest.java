package org.springframework.samples.dwarf.tablero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.dwarf.carta.Carta;

@DataJpaTest
public class TableroRepositoryTest {

    @Autowired
    TableroRepository tableroRepository;

    public Tablero tableroPrueba;

    @Test
    public void testFindAllCartas() {
        List<Carta> cartas = tableroRepository.findAllCartas();
        assertNotNull(cartas);
        assertFalse(cartas.isEmpty());
        assertEquals(66, cartas.size());
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
   
}
