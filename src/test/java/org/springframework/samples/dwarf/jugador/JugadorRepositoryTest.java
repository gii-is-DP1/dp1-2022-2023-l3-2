package org.springframework.samples.dwarf.jugador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JugadorRepositoryTest {
    @Autowired
    JugadorRepository jugadorrepo;

    @Test
    public void findJugadoresByUsername() {
        List<Jugador> jugadores = jugadorrepo.findByUserUsername("dandiasua");
        assertNotNull(jugadores);
        assertFalse(jugadores.isEmpty());
        assertEquals(1, jugadores.size());
    }
}
