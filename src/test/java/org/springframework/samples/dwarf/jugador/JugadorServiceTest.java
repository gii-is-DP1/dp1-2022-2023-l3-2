package org.springframework.samples.dwarf.jugador;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.dwarf.user.User;
import org.springframework.samples.dwarf.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class JugadorServiceTest {
    
    @Autowired
    protected JugadorService jugadorService;

    @Autowired
    protected UserService userService;

    @Test
    public void shouldFindOwnerById() {
        Jugador alegarsan11 = jugadorService.findOwnerById(1);
        User alegarsan11User = userService.findUser("alegarsan11").get();

        assertThat(alegarsan11.getAcero()).isEqualTo(0);
        assertThat(alegarsan11.getHierro()).isEqualTo(0);
        assertThat(alegarsan11.getMedalla()).isEqualTo(0);
        assertThat(alegarsan11.getObjeto()).isEqualTo(0);
        assertThat(alegarsan11.getOro()).isEqualTo(0);
        assertThat(alegarsan11.getPosicionFinal()).isEqualTo(null);
        assertThat(alegarsan11.getEnanosDisponibles()).isEqualTo(2);

        assertThat(alegarsan11.isEsespectador()).isEqualTo(false);
        assertThat(alegarsan11.isPrimerjugador()).isEqualTo(true);
        assertThat(alegarsan11.isTurno()).isEqualTo(true);

        assertThat(alegarsan11.getUser()).isEqualTo(alegarsan11User);
    }

    @Test
    public void shouldFindAll() {
        List<Jugador> jugadores = jugadorService.findAll();

        Jugador alegarsan11 = jugadores.get(0);
        assertThat(alegarsan11.getAcero()).isEqualTo(0);
        assertThat(alegarsan11.getHierro()).isEqualTo(0);
        assertThat(alegarsan11.getMedalla()).isEqualTo(0);
        assertThat(alegarsan11.getObjeto()).isEqualTo(0);
        assertThat(alegarsan11.getOro()).isEqualTo(0);
        assertThat(alegarsan11.getPosicionFinal()).isEqualTo(null);
        assertThat(alegarsan11.getEnanosDisponibles()).isEqualTo(2);

        assertThat(alegarsan11.isEsespectador()).isEqualTo(false);
        assertThat(alegarsan11.isPrimerjugador()).isEqualTo(true);
        assertThat(alegarsan11.isTurno()).isEqualTo(true);

        User alegarsan11User = userService.findUser("alegarsan11").get();
        assertThat(alegarsan11.getUser()).isEqualTo(alegarsan11User);
    }

    @Test
    public void shouldFindJugadorUser() {
        List<Jugador> jugadores = jugadorService.findJugadorUser("alegarsan11");

        Jugador alegarsan11 = jugadores.get(0);
        assertThat(alegarsan11.getAcero()).isEqualTo(0);
        assertThat(alegarsan11.getHierro()).isEqualTo(0);
        assertThat(alegarsan11.getMedalla()).isEqualTo(0);
        assertThat(alegarsan11.getObjeto()).isEqualTo(0);
        assertThat(alegarsan11.getOro()).isEqualTo(0);
        assertThat(alegarsan11.getPosicionFinal()).isEqualTo(null);
        assertThat(alegarsan11.getEnanosDisponibles()).isEqualTo(2);

        assertThat(alegarsan11.isEsespectador()).isEqualTo(false);
        assertThat(alegarsan11.isPrimerjugador()).isEqualTo(true);
        assertThat(alegarsan11.isTurno()).isEqualTo(true);

        User alegarsan11User = userService.findUser("alegarsan11").get();
        assertThat(alegarsan11.getUser()).isEqualTo(alegarsan11User);
    }

    @Test
    public void shouldCreateJugadorByUsername() {
        Jugador alegarsan12 = jugadorService.createJugadorByUsername("alegarsan11", false);

        assertThat(alegarsan12.getId()).isEqualTo(2);
        assertThat(alegarsan12.isPrimerjugador()).isEqualTo(false);
        User alegarsan11User = userService.findUser("alegarsan11").get();
        assertThat(alegarsan12.getUser()).isEqualTo(alegarsan11User);
    }

    @Test
    public void shouldDeleteJugador() {
        Jugador alegarsan11 = jugadorService.findOwnerById(1);
        jugadorService.deleteJugador(alegarsan11);
        assertThat(jugadorService.findAll().size()).isEqualTo(0);
    }

}

