package org.springframework.samples.dwarf.jugador;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JugadorRepositoryTest {

    @Autowired
    JugadorRepository jugadorRepository;

    @Test
    public void deleteJugadorTest() {
        Jugador jug = jugadorRepository.findById(1);
        jugadorRepository.delete(jug);
        assertThat(jugadorRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void findAllTest() {
        Collection<Jugador> jugadores = jugadorRepository.findAll();
        assertNotNull(jugadores);
        assertThat(jugadores.size()).isEqualTo(1);
    }

    @Test
    public void findByIdTest() {
        Jugador jug = jugadorRepository.findById(1);
        assertThat(jug.getAcero()).isEqualTo(0);
        assertThat(jug.getHierro()).isEqualTo(0);
        assertThat(jug.getMedalla()).isEqualTo(0);
        assertThat(jug.getObjeto()).isEqualTo(0);
        assertThat(jug.getOro()).isEqualTo(0);
        assertThat(jug.getPosicionFinal()).isEqualTo(null);
        assertThat(jug.getEnanosDisponibles()).isEqualTo(2);
        assertThat(jug.isEsespectador()).isEqualTo(false);
        assertThat(jug.isPrimerjugador()).isEqualTo(true);
        assertThat(jug.isTurno()).isEqualTo(true);
    }

    @Test
    public void findByUserUsernameTest() {
        List<Jugador> jugadores = jugadorRepository.findByUserUsername("alegarsan11");

        Jugador jug = jugadores.get(0);
        assertThat(jug.getAcero()).isEqualTo(0);
        assertThat(jug.getHierro()).isEqualTo(0);
        assertThat(jug.getMedalla()).isEqualTo(0);
        assertThat(jug.getObjeto()).isEqualTo(0);
        assertThat(jug.getOro()).isEqualTo(0);
        assertThat(jug.getPosicionFinal()).isEqualTo(null);
        assertThat(jug.getEnanosDisponibles()).isEqualTo(2);
        assertThat(jug.isEsespectador()).isEqualTo(false);
        assertThat(jug.isPrimerjugador()).isEqualTo(true);
        assertThat(jug.isTurno()).isEqualTo(true);
    }


}
