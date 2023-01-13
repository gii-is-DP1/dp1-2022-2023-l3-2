package org.springframework.samples.dwarf.jugador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.dwarf.tablero.Tablero;

@DataJpaTest
public class JugadorTest {

    @Autowired
    JugadorRepository jr;

    @Autowired(required = false)
    EntityManager em;

    @Test
    public void tests() {
        entityExist();
        jugadorInicial();
    }

    private void jugadorInicial() {

        Jugador j = Jugador.crearJugadorInicial(true);
        assertEquals(j.getAcero(), 0);
    }

    public void entityExist() {
        Entity entityAnnotation = Jugador.class.getAnnotation(Entity.class);
        assertNotNull(entityAnnotation, "No es una entidad");
    }


}
