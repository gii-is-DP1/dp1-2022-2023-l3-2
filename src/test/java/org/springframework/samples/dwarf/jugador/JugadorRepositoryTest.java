package org.springframework.samples.dwarf.jugador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JugadorRepositoryTest {
    @Autowired
    JugadorRepository jugadorrepo;
    /*
     * @Test
     * public void testFindByUsername() {
     * List<Jugador> jugadores = jugadorrepo.findByUserUsername("dandiasua");
     * assertNotNull(jugadores);
     * assertFalse(jugadores.isEmpty());
     * assertEquals(1, jugadores.size());
     * }
     * 
     * @Test
     * public void testFindById() {
     * Integer id = 2;
     * Jugador j = jugadorrepo.findById(id);
     * assertNotNull(j);
     * }
     */

    // @Test
    // public void testFindByLastName() {
    // String lastName = "Diañez";
    // Collection<Jugador> jugadores = jugadorrepo.findByLastName(lastName);
    // assertNotNull(jugadores);
    // assertFalse(jugadores.isEmpty());
    // assertEquals(1, jugadores.size());
    // }
}
